package org.unibl.etf.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.rest_api.model.db.TransportationDevice;

import java.util.List;

@Repository
public interface TransportationDeviceRepository extends JpaRepository<TransportationDevice, Integer> {
    @Query("SELECT td FROM TransportationDevice td WHERE td.status = 'Available'")
    List<TransportationDevice> findAllAvailable();
}
