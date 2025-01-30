package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.ElectricBicycle;

@Repository
public interface ElectricBicycleRepository extends GenericRepository<ElectricBicycle, Integer> {
}
