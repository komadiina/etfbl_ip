package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.ElectricScooter;


@Repository
public interface ElectricScooterRepository extends GenericRepository<ElectricScooter, Integer> {
}
