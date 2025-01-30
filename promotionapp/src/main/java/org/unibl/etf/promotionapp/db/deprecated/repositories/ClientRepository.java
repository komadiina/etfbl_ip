package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.Client;


@Repository
public interface ClientRepository extends GenericRepository<Client, Integer> {
}
