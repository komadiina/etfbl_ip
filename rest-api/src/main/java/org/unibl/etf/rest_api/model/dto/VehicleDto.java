package org.unibl.etf.rest_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehicleDto {
    private String UUID;
    private String model;
    private String description;
    private String status;
    private double purchasePrice;
    private int manufacturerID;
    private String deviceType;
    private Date acquisitionDate;
}
