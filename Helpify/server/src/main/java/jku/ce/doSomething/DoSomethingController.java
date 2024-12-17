package jku.ce.doSomething;

import jku.ce.config.JwtService;
import jku.ce.doSomething.rest.AddTaskRequest;
import jku.ce.doSomething.rest.GetNearestTasksResponse;
import jku.ce.exceptions.NoAccessException;
import jku.ce.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doSomething")
@RequiredArgsConstructor
public class DoSomethingController {

    private final DoSomethingService doSomethingService;
    private final JwtService jwtService;

    @GetMapping("/getNearestTasks")
    public ResponseEntity<GetNearestTasksResponse> getNearestTasks(@RequestHeader("Authorization") String authorization) throws NoAccessException {

        String token = authorization.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        GetNearestTasksResponse response = doSomethingService.getNearestTasks(token);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/addTask")
    public ResponseEntity<Void> addTask(@RequestHeader("Authorization") String authorization, @RequestBody AddTaskRequest request) throws NoAccessException {

        String token = authorization.substring(7);
        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return doSomethingService.addTask(token, request);
    }

}
