package org.yamashiro0110.sample.data.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("blog")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blog {
    @Id
    @Column("blog_id")
    private int id;
    private String note;
    @MappedCollection(idColumn = "blog_id", keyColumn = "blog_id")
    private List<Comment> comments;
}
