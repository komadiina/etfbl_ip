package org.unibl.etf.promotionapp.db.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.unibl.etf.promotionapp.db.models.Post;
import org.unibl.etf.promotionapp.db.models.Promotion;

import java.util.List;

@ApplicationScoped
public class PromotionRepository {
    @PersistenceContext(name = "default")
    private EntityManager em;

    private EntityManagerFactory emf;

    public PromotionRepository() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    public Promotion create(Promotion post) {
        try {
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
            return post;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        }
    }

    public Promotion read(int id) {
        try {
            return em.find(Promotion.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Promotion> readAll() {
        try {
            return em.createQuery("select p from Post p", Promotion.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
