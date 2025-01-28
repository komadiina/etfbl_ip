package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "electricscooters")
@PrimaryKeyJoinColumn(name = "ScooterID", referencedColumnName = "DeviceID")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricScooter extends TransportationDevice {
    @Column(name = "MaxSpeed", nullable = false, columnDefinition = "int")
    private int maxSpeed;
}
