package org.unibl.etf.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.rest_api.model.db.ElectricBicycle;

public interface ElectricBicycleRepository extends JpaRepository<ElectricBicycle, Integer> {
}
