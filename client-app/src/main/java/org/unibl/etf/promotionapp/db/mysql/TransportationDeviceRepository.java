package org.unibl.etf.promotionapp.db.mysql;

import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.unibl.etf.promotionapp.db.models.TransportationDevice;
import java.util.List;

@ApplicationScoped
@LocalBean
public class TransportationDeviceRepository {
    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public TransportationDeviceRepository() {
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

    public List<TransportationDevice> findAllAvailable() {
        return entityManager
                .createQuery("SELECT td FROM TransportationDevice td WHERE td.status = 'Available'", TransportationDevice.class)
                .getResultList();
    }

    public List<TransportationDevice> findAll() {
        return entityManager.createQuery("from TransportationDevice", TransportationDevice.class).getResultList();
    }

    public List<TransportationDevice> findVehicles() {
        return entityManager
                .createQuery("SELECT td FROM TransportationDevice td WHERE td.deviceType = 'Vehicle' AND td.status = 'Available'", TransportationDevice.class)
                .getResultList();
    }

    public List<TransportationDevice> findBicycles() {
        return entityManager
                .createQuery("SELECT td FROM TransportationDevice td WHERE td.deviceType = 'ElectricBicycle' AND td.status = 'Available'", TransportationDevice.class)
                .getResultList();
    }

    public List<TransportationDevice> findScooters() {
        return entityManager
                .createQuery("SELECT td FROM TransportationDevice td WHERE td.deviceType = 'ElectricScooter' AND td.status = 'Available'", TransportationDevice.class)
                .getResultList();
    }

    public TransportationDevice updatePosition(int deviceID, int dropoffX, int dropoffY) {
        try {
            entityManager = Persistence
                    .createEntityManagerFactory("default")
                    .createEntityManager();
            entityManager.getTransaction().begin();

            TransportationDevice td = entityManager.find(TransportationDevice.class, deviceID);
            td.setPosX(dropoffX);
            td.setPosY(dropoffY);

            entityManager.merge(td);
            entityManager.getTransaction().commit();
            entityManager.close();

            return td;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TransportationDevice updateStatus(int deviceID, String status) {
        try {
            entityManager = Persistence
                    .createEntityManagerFactory("default")
                    .createEntityManager();
            entityManager.getTransaction().begin();

            TransportationDevice td = entityManager.find(TransportationDevice.class, deviceID);
            td.setStatus(status);

            entityManager.merge(td);
            entityManager.getTransaction().commit();
            entityManager.close();

            return td;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TransportationDevice findByDeviceID(int deviceID) {
        try {
            entityManager = Persistence
                    .createEntityManagerFactory("default")
                    .createEntityManager();
            return entityManager.find(TransportationDevice.class, deviceID);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
