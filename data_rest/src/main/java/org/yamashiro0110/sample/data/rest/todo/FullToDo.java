package org.yamashiro0110.sample.data.rest.todo;

import org.springframework.data.rest.core.config.Projection;
import org.yamashiro0110.sample.domain.todo.ToDo;

import java.time.LocalDateTime;

@Projection(name = "FullToDo", types = {ToDo.class})
public interface FullToDo {

    long getId();

    String getNote();

    LocalDateTime getCreated();

    LocalDateTime getUpdated();

}
