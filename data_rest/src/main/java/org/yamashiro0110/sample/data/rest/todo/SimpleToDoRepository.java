package org.yamashiro0110.sample.data.rest.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.yamashiro0110.sample.domain.todo.ToDo;

@RepositoryRestResource(path = "todo_simple", excerptProjection = SimpleToDo.class)
public interface SimpleToDoRepository extends CrudRepository<ToDo, Long> {
}
