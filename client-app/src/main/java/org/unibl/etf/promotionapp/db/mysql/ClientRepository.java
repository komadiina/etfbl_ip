package org.unibl.etf.promotionapp.db.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.unibl.etf.promotionapp.db.models.Client;
@ApplicationScoped
public class ClientRepository {
    @PersistenceContext(name = "default")
    private EntityManager entityManager;

    public ClientRepository() {}

    @Transactional
    public Client register(String username, String password, String phoneNumber, String email, String idCardNumber, String firstName, String lastName, String passportID) {
        Client client = new Client();

        client.setUsername(username);
        client.setPassword(new BCryptPasswordEncoder().encode(password));
        client.setPhoneNumber(phoneNumber);
        client.setEmail(email);
        client.setIdCardNumber(idCardNumber);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPassportID(passportID);
        client.setUserType("Client");
        client.setBalance(999.0);

        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(client);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }

        return client;
    }

    public Client login(String username, String password) {
        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
            Client client = entityManager.createQuery("SELECT c FROM Client c WHERE c.username = :username", Client.class)
                    .setParameter("username", username)
                    .getSingleResult();
            System.out.println(client.getPassword());
            System.out.println(new BCryptPasswordEncoder().encode(password));
            var result = new BCryptPasswordEncoder().matches(password, client.getPassword()) ? client : null;
            entityManager.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Client editUsername(String username, String newUsername) {
        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
            entityManager.getTransaction().begin();
            Client client = entityManager.createQuery("SELECT c FROM Client c WHERE c.username = :username", Client.class)
                    .setParameter("username", username)
                    .getSingleResult();
            client.setUsername(newUsername);
            entityManager.merge(client);
            entityManager.getTransaction().commit();
            entityManager.close();
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Client editPassword(String username, String newPassword) {
        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
            entityManager.getTransaction().begin();
            Client client = entityManager.createQuery("SELECT c from Client c where c.username = :username", Client.class)
                    .setParameter("username", username)
                    .getSingleResult();
            client.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            entityManager.merge(client);
            entityManager.getTransaction().commit();
            entityManager.close();
            return client;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    public Client findByUsername(String username) {
        try {
            entityManager = Persistence.createEntityManagerFactory("default").createEntityManager();
            Client client = entityManager.createQuery("SELECT c FROM Client c WHERE c.username = :username", Client.class)
                    .setParameter("username", username)
                    .getSingleResult();
            entityManager.close();
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
