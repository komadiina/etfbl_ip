package org.unibl.etf.promotionapp.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransportationDeviceBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String UUID;
    private String deviceType;
    private String model;
    private String description;
    private String status;
}
