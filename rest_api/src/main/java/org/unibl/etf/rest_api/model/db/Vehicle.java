package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vehicles")
@PrimaryKeyJoinColumn(name = "VehicleID", referencedColumnName = "DeviceID")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends TransportationDevice {
//    @Column(name = "VehicleID", updatable = false, unique = true, nullable = false, columnDefinition = "serial")
    private int vehicleID;
}
