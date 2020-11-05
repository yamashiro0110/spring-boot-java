package org.yamashiro0110.sample.data.rest.todo;

import org.springframework.data.rest.core.config.Projection;
import org.yamashiro0110.sample.domain.todo.ToDo;

@Projection(name = "SimpleToDo", types = ToDo.class)
public interface SimpleToDo {
    long getId();

    String getNote();
}
