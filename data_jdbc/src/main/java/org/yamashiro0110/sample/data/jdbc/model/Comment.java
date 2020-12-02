package org.yamashiro0110.sample.data.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("comment")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {
    @Id
    @Column("comment_id")
    private int id;
    private String note;
}
