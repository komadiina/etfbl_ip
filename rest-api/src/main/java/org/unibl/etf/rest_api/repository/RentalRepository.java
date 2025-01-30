package org.unibl.etf.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.rest_api.model.db.Rental;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query("SELECT r FROM Rental r WHERE r.deviceID = ?1")
    List<Rental> findAllByDeviceID(int deviceID);
}
