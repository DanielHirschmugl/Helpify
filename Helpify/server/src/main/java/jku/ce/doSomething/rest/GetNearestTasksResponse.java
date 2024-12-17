package jku.ce.doSomething.rest;

import jku.ce.doSomething.rest.help.GetNearestTasksHelp;
import jku.ce.task.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetNearestTasksResponse {
    List<GetNearestTasksHelp> taskCards;
}
