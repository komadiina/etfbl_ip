package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PostID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int postID;

    @Column(name = "EmployeeID", nullable = false, columnDefinition = "int")
    private int employeeID;

    @Column(name = "Title", nullable = false, columnDefinition = "varchar(255)")
    private String title;

    @Column(name = "Content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "CreatedAt", nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt;
}
