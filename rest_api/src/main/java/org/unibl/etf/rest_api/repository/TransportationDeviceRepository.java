package org.unibl.etf.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.rest_api.model.db.TransportationDevice;

@Repository
public interface TransportationDeviceRepository extends JpaRepository<TransportationDevice, Integer> {
}
