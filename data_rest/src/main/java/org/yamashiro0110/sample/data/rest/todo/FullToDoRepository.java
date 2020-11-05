package org.yamashiro0110.sample.data.rest.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.yamashiro0110.sample.domain.todo.ToDo;

@RepositoryRestResource(path = "todo_full", excerptProjection = FullToDo.class)
public interface FullToDoRepository extends CrudRepository<ToDo, Long> {
}
