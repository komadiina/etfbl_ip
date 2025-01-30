package org.unibl.etf.promotionapp.db.deprecated.repositories;

import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import org.unibl.etf.promotionapp.db.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);
}
