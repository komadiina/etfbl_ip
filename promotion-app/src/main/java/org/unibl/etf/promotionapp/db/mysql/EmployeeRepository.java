package org.unibl.etf.promotionapp.db.mysql;

import com.mysql.cj.xdevapi.Client;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.unibl.etf.promotionapp.db.models.Employee;

import java.util.List;

@ApplicationScoped
public class EmployeeRepository {
    @PersistenceContext(name = "default")
    private EntityManager em;

    private EntityManagerFactory emf;

    public EmployeeRepository() {
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    public Employee read(int id) {
        try {
            return em.find(Employee.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Employee> readAll() {
        try {
            return em.createQuery("select e from Employee e", Employee.class).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    // save, update -- todo,
    // pitanje jel trebaju uopste

    public Employee login(String username, String password) {
        try {
            Employee employee = (Employee) em.createQuery("select e from Employee e where e.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
            if (employee != null) {
                if (!employee.getRole().equals("Manager")) {
                    return null;
                }
                else if (new BCryptPasswordEncoder().matches(password, employee.getPassword())) {
                    return employee;
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
