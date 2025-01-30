package org.unibl.etf.promotionapp.db.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.unibl.etf.promotionapp.db.models.Rental;
import org.unibl.etf.promotionapp.db.models.RentalPrice;

import java.util.List;

public class RentalPriceRepository {
    @PersistenceContext(name = "default")
    private EntityManager entityManager;

    public RentalPriceRepository() {
        entityManager = Persistence
                .createEntityManagerFactory("default")
                .createEntityManager();
    }

    public void close() {
        try {
            if (entityManager != null) {
                entityManager.close();
                entityManager = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        try {
            if (entityManager == null)
                entityManager = Persistence
                        .createEntityManagerFactory("default")
                        .createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RentalPrice findByDeviceID(int deviceID) {
        try {
            return entityManager
                    .createQuery("SELECT r FROM RentalPrice r WHERE r.deviceID = :deviceID and r.isActive = true", RentalPrice.class)
                    .setParameter("deviceID", deviceID)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<RentalPrice> findAll() {
        return entityManager.createQuery("from RentalPrice", RentalPrice.class).getResultList();
    }
}
