package org.yamashiro0110.sample.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToDo {
    @Id
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String note;
}
