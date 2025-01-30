package org.unibl.etf.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.rest_api.model.db.Breakdown;

import java.util.List;

@Repository
public interface BreakdownRepository extends JpaRepository<Breakdown, Integer> {
    @Query("SELECT b FROM Breakdown b WHERE b.deviceID = ?1")
    List<Breakdown> findByAllDeviceID(int deviceID);

    @Query("SELECT b FROM Breakdown b WHERE b.deviceID = ?1 AND b.active = true")
    Breakdown findByDeviceIDActive(int deviceID);
}
