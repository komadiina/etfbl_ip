package org.unibl.etf.promotionapp.db.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentalID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int rentalID;

    @Column(name = "ClientID", nullable = false, columnDefinition = "int")
    private int clientID;

    @Column(name = "DeviceID", nullable = false, columnDefinition = "int")
    private int deviceID;

    @Column(name = "PriceID", nullable = false, columnDefinition = "int")
    private int priceID;

    @Column(name = "DriversLicense", nullable = false, columnDefinition = "varchar(100)")
    private String driversLicense;

    @Column(name = "StartDateTime", nullable = false, columnDefinition = "datetime")
    private LocalDateTime startDateTime;

    @Column(name = "EndDateTime", nullable = true, columnDefinition = "datetime")
    private LocalDateTime endDateTime;

    @Column(name = "PickupX", nullable = false, columnDefinition = "int")
    private int pickupX;

    @Column(name = "PickupY", nullable = false, columnDefinition = "int")
    private int pickupY;

    @Column(name = "DropoffX", columnDefinition = "int")
    private Integer dropoffX;

    @Column(name = "DropoffY", columnDefinition = "int")
    private Integer dropoffY;

    @Column(name = "Duration", columnDefinition = "int")
    private Integer duration;

    @Column(name = "PaymentCard", columnDefinition = "varchar(64)")
    private String paymentCard;
}
