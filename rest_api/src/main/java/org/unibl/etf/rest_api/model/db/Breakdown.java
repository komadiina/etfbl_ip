package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "breakdowns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Breakdown {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BreakdownID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int breakdownID;

    @Column(name = "DeviceID", nullable = false, columnDefinition = "int")
    private int deviceID;

    @Column(name = "IsActive", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean active;

    @Column(name = "Description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "DateTimeRecorded", nullable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime dateTimeRecorded;
}
