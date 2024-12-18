package jku.ce.doSomething;

import jku.ce.config.JwtService;
import jku.ce.doSomething.rest.AddLocationRequest;
import jku.ce.doSomething.rest.AddTaskRequest;
import jku.ce.doSomething.rest.GetNearestTasksResponse;
import jku.ce.doSomething.rest.help.GetNearestTasksHelp;
import jku.ce.exceptions.NoAccessException;
import jku.ce.location.*;
import jku.ce.task.Task;
import jku.ce.task.TaskRepository;
import jku.ce.user.Skill;
import jku.ce.user.User;
import jku.ce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoSomethingService {

    private final TaskRepository taskRepository;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private final FederalStateRepository federalStateRepository;
    private final RegionRepository regionRepository;
    private final CommuneRepository communeRepository;
    private final LocationRepository locationRepository;


    public GetNearestTasksResponse getNearestTasks(String token) throws NoAccessException {
        if(userRepository.findByUsername((jwtService.extractUsername(token))).isEmpty()) {
            throw new NoAccessException("Benutzer nicht gefunden");
        }

        User user = userRepository.findByUsername(jwtService.extractUsername(token)).get();

        List<Task> taskList = taskRepository.findAll().stream()
                .sorted(Comparator.comparingInt((Task x) -> {

                    if (x.getUser().getLocation().getCommune().equals(user.getLocation().getCommune())) {
                        return 1;
                    }

                    if (x.getUser().getLocation().getRegion().equals(user.getLocation().getRegion())) {
                        return 2;
                    }

                    if (x.getUser().getLocation().getFederalState().equals(user.getLocation().getFederalState())) {
                        return 3;
                    }

                    if (x.getUser().getLocation().getState().equals(user.getLocation().getState())) {
                        return 4;
                    }
                    return 5;
                }))
                .toList();

            List<GetNearestTasksHelp> helpList = new ArrayList<>();
            for (Task task : taskList) {
                helpList.add(new GetNearestTasksHelp(
                        task.getUser().getUsername(),
                        task.getUser().getPhonenumber(),
                        task.getUser().getLocation().getCommune().getStreet(),
                        task.getUser().getLocation().getCommune().getNumber(),
                        task.getUser().getLocation().getCommune().getName(),
                        task.getUser().getLocation().getCommune().getPostalCode(),
                        task.getUser().getLocation().getRegion().getName(),
                        task.getUser().getLocation().getFederalState().getName(),
                        task.getUser().getLocation().getState().getName(),
                        task
                        )
                );
            }

            return new GetNearestTasksResponse(helpList);
    }

    public ResponseEntity<Void> addTask(String token, AddTaskRequest request) throws NoAccessException {
        if(userRepository.findByUsername((jwtService.extractUsername(token))).isEmpty()) {
            throw new NoAccessException("Benutzer nicht gefunden");
        }

        User user = userRepository.findByUsername(jwtService.extractUsername(token)).get();

        Task task = new Task();
        task.setUser(user);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        for(Skill skill : request.getSkills()) {
            task.addSkill(skill);
        }
        task.setDone(false);
        user.addTask(task);
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> addLocation(String token, AddLocationRequest request) throws NoAccessException {
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoAccessException("Benutzer nicht gefunden"));

        State state = stateRepository.findByName(request.getStateName())
                .orElseGet(() -> State.builder()
                        .name(request.getStateName())
                        .federalStates(new ArrayList<>())
                        .build());

        FederalState federalState = federalStateRepository.findByName(request.getFederalStateName())
                .orElseGet(() -> {
                    FederalState newFederalState = FederalState.builder()
                            .name(request.getFederalStateName())
                            .state(state)
                            .regions(new ArrayList<>())
                            .build();
                    state.getFederalStates().add(newFederalState);
                    return newFederalState;
                });

        Region region = regionRepository.findByNameAndFederalState(request.getRegionName(), federalState)
                .orElseGet(() -> {
                    Region newRegion = Region.builder()
                            .name(request.getRegionName())
                            .federalState(federalState)
                            .communes(new ArrayList<>())
                            .build();
                    federalState.getRegions().add(newRegion);
                    return newRegion;
                });

        Commune commune = communeRepository.findByNameAndPostalCode(request.getCommuneName(), request.getPostalCode())
                .orElseGet(() -> {
                    Commune newCommune = Commune.builder()
                            .name(request.getCommuneName())
                            .postalCode(request.getPostalCode())
                            .street(request.getStreetName())
                            .number(request.getStreetNumber())
                            .region(region)
                            .build();
                    region.getCommunes().add(newCommune);
                    return newCommune;
                });

        Location location = Location.builder()
                .commune(commune)
                .region(region)
                .federalState(federalState)
                .state(state)
                .build();

        locationRepository.save(location);
        user.setLocation(location);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
