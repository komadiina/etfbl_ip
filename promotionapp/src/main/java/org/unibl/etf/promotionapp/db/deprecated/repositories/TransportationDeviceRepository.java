package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.TransportationDevice;

import java.util.List;

@Repository
public interface TransportationDeviceRepository {
    List<TransportationDevice> findAllAvailable();
    List<TransportationDevice> findAll();
}
