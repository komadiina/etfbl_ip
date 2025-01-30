package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.Vehicle;

@Repository
public interface VehicleRepository extends GenericRepository<Vehicle, Integer> {
}
