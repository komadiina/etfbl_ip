package org.unibl.etf.rest_api.model.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "electricbicycles")
@PrimaryKeyJoinColumn(name = "BicycleID", referencedColumnName = "DeviceID")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricBicycle extends TransportationDevice {
    @Column(name = "Autonomy", nullable = false, columnDefinition = "int")
    private int autonomy;
}
