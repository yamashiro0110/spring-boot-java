package org.yamashiro0110.sample.domain.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "todo")
public interface ToDoRepository extends CrudRepository<ToDo, Long> {
}
