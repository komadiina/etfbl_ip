package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "transportationdevices")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportationDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DeviceID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int deviceID;

    @Column(name = "UniqueIdentifier", unique = true, nullable = false, columnDefinition = "varchar(50)")
    private String UUID;

    @Column(name = "DeviceType", nullable = false, columnDefinition = "enum('Vehicle', 'ElectricBicycle', 'ElectricScooter')")
    private String deviceType;

    @Column(name = "AcquisitionDate", nullable = false, columnDefinition = "date")
    private Date acquisitionDate;

    @Column(name = "PurchasePrice", nullable = false, columnDefinition = "decimal(10,2)")
    private double purchasePrice;

    @Column(name = "ManufacturerID", columnDefinition = "int")
    private int manufacturerID;

    @Column(name = "Model", nullable = false, columnDefinition = "varchar(100)")
    private String model;

    @Column(name = "Description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "Status", nullable = false, columnDefinition = "enum('Available', 'Rented', 'Broken')")
    private String status;

    @Column(name = "PosX", nullable = false, columnDefinition = "int default 0")
    private int posX;

    @Column(name = "PosY", nullable = false, columnDefinition = "int default 0")
    private int posY;
}
