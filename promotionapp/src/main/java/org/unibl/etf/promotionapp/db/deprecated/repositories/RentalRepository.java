package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.Rental;

import java.util.List;

@Repository
public interface RentalRepository extends GenericRepository<Rental, Integer> {
    @Query("SELECT r FROM Rental r WHERE r.deviceID = ?1")
    List<Rental> findAllByDeviceID(int deviceID);
}
