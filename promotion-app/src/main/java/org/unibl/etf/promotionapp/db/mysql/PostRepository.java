package org.unibl.etf.promotionapp.db.mysql;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.unibl.etf.promotionapp.db.models.Post;

import java.util.List;

@ApplicationScoped
public class PostRepository {
    @PersistenceContext(name = "default")
    private EntityManager em;

    private EntityManagerFactory emf;

    public PostRepository() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    public Post read(int id) {
        try {
            return em.find(Post.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Post> readAll() {
        try {
            return em.createQuery("select p from Post p", Post.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Post create(Post post) {
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
}
