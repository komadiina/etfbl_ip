package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@Entity
@Table(name = "manufacturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ManufacturerID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int manufacturerID;

    @Column(name = "Name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "Country", nullable = false, columnDefinition = "varchar(50)")
    private String country;

    @Column(name = "Address", nullable = false, columnDefinition = "text")
    private String address;

    @Column(name = "Phone", nullable = false, columnDefinition = "varchar(20)")
    private String phone;

    @Column(name = "Fax", nullable = false, columnDefinition = "varchar(20)")
    private String fax;

    @Column(name = "Email", nullable = false, columnDefinition = "varchar(100)")
    private String email;
}
