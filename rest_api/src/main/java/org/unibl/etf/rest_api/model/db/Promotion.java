package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PromotionID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int promotionID;

    @Column(name = "Title", nullable = false, columnDefinition = "varchar(255)")
    private String title;

    @Column(name = "Description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "StartDate", nullable = false, columnDefinition = "date")
    private Date startDate;

    @Column(name = "EndDate", nullable = false, columnDefinition = "date")
    private Date endDate;
}
