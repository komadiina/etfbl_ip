package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.RentalPrice;

import java.util.List;

@Repository
public interface RentalPriceRepository extends GenericRepository<RentalPrice, Integer> {
    @Query("SELECT r FROM RentalPrice r WHERE r.deviceID = ?1")
    List<RentalPrice> findByDeviceID(int deviceID);
}
