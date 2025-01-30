package org.unibl.etf.rest_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.unibl.etf.rest_api.model.db.TransportationDevice;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeviceWithLocationDto {
    private TransportationDevice device;
    private int x;
    private int y;
}
