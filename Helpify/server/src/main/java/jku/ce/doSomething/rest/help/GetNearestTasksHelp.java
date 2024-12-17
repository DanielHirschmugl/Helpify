package jku.ce.doSomething.rest.help;

import jku.ce.task.Task;
import jku.ce.user.Skill;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetNearestTasksHelp {
    String username;
    int phonenumber;
    Task task;
}
