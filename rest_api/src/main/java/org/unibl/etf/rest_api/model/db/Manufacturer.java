package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.security.PrivateKey;

@Entity
@Table(name = "manufacturers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ManufacturerID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int manufacturerID;

    @Column(name = "Name", nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "Country", nullable = false, columnDefinition = "varchar(50)")
    private String country;

    @Column(name = "Address", columnDefinition = "text")
    private String address;

    @Column(name = "Phone", columnDefinition = "varchar(20)")
    private String phone;

    @Column(name = "Fax", columnDefinition = "varchar(20)")
    private String fax;

    @Column(name = "Email", columnDefinition = "varchar(100)")
    private String email;
}
