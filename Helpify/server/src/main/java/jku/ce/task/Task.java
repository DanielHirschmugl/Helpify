package jku.ce.task;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jku.ce.user.Skill;
import jku.ce.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean done = false;

    @ElementCollection(targetClass = Skill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "task_skills", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "skill")
    private List<Skill> skills;

    public void addSkill(Skill skill){
        if(skills == null) skills = new ArrayList<>();
        skills.add(skill);
    }
}
