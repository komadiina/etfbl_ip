package org.unibl.etf.promotionapp.db.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.unibl.etf.promotionapp.beans.RentalInfo;
import org.unibl.etf.promotionapp.db.models.Rental;

import java.util.List;

@ApplicationScoped
public class RentalRepository {
    @PersistenceContext(name = "default")
    private EntityManager entityManager;

    public RentalRepository() {}


    public Rental findActiveRentalByClientID(int clientID) {
        try {
            entityManager = Persistence
                    .createEntityManagerFactory("default")
                    .createEntityManager();
            var result = entityManager
                    .createQuery("SELECT r FROM Rental r WHERE r.clientID = :clientID AND r.endDateTime IS NULL", Rental.class)
                    .setParameter("clientID", clientID)
                    .getSingleResult();
            entityManager.close();
            return result;
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public Rental update(Rental activeRental) {
        entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
        entityManager.getTransaction().begin();
        var result = entityManager.merge(activeRental);
        entityManager.getTransaction().commit();
        entityManager.close();
        return result;
    }

    public Rental findLastRentalByDeviceID(int deviceID) {
        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
            var result = entityManager
                    .createQuery("SELECT r FROM Rental r WHERE r.deviceID = :deviceID ORDER BY r.rentalID DESC", Rental.class)
                    .setParameter("deviceID", deviceID)
                    .setMaxResults(1)
                    .getSingleResult();
            entityManager.close();
            return result;
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    public void save(Rental newRental) {
        entityManager = Persistence
                .createEntityManagerFactory("default")
                .createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(newRental);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Rental findRentalPriceByDeviceID(int deviceID) {
        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();

            var result = entityManager
                    .createQuery("SELECT r FROM RentalPrice r WHERE r.deviceID = :deviceID", Rental.class)
                    .setParameter("deviceID", deviceID)
                    .getSingleResult();
            entityManager.close();
            return result;
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Rental> findAllByClientID(int userBean) {
        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
            var result = entityManager
                    .createQuery("SELECT r FROM Rental r WHERE r.clientID = :userBean", Rental.class)
                    .setParameter("userBean", userBean)
                    .getResultList();
            entityManager.close();
            return result;
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
