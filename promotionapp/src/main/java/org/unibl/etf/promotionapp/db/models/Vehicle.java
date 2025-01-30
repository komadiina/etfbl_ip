package org.unibl.etf.promotionapp.db.models;

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
public class Vehicle extends TransportationDevice {
}
