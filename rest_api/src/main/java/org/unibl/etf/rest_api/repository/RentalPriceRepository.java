package org.unibl.etf.rest_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.rest_api.model.db.RentalPrice;

import java.util.List;

@Repository
public interface RentalPriceRepository extends JpaRepository<RentalPrice, Integer> {
    @Query("SELECT r FROM RentalPrice r WHERE r.deviceID = ?1")
    List<RentalPrice> findByDeviceID(int deviceID);

    @Query("SELECT r FROM RentalPrice r WHERE r.deviceID = ?1")
    Page<RentalPrice> findByDeviceID(int deviceID, Pageable pageable);
}
