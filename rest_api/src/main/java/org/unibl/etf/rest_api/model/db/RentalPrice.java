package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rentalprices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PriceID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int priceID;

    @Column(name = "DeviceID", nullable = false, columnDefinition = "int")
    private int deviceID;

    @Column(name = "PricePerMinute", nullable = false, columnDefinition = "decimal(5,2)")
    private double pricePerMinute;

    @Column(name = "IsActive", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive = true;
}
