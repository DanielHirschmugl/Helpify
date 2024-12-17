package jku.ce.doSomething.rest;

import jku.ce.user.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddTaskRequest {
    private String title;
    private String description;
    private List<Skill> skills;
}
