package jku.ce.doSomething.rest.help;

import jku.ce.task.Task;
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
public class GetNearestTasksHelp {
    String username;
    String phonenumber;
    Task task;
}
