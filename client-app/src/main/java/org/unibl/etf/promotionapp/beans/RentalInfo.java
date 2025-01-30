package org.unibl.etf.promotionapp.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.unibl.etf.promotionapp.db.models.Rental;
import org.unibl.etf.promotionapp.db.models.RentalPrice;
import org.unibl.etf.promotionapp.db.models.TransportationDevice;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class RentalInfo extends Rental {
    public TransportationDevice device;
    public RentalPrice price;

    public RentalInfo(Rental rental) {
        this.setDeviceID(rental.getDeviceID());
        this.setClientID(rental.getClientID());
        this.setPriceID(rental.getPriceID());
        this.setDriversLicense(rental.getDriversLicense());
        this.setStartDateTime(rental.getStartDateTime());
        this.setEndDateTime(rental.getEndDateTime());
        this.setPickupX(rental.getPickupX());
        this.setPickupY(rental.getPickupY());
        this.setDropoffX(rental.getDropoffX());
        this.setDropoffY(rental.getDropoffY());
        this.setDuration(rental.getDuration());
        this.setPaymentCard(rental.getPaymentCard());
    }
}
