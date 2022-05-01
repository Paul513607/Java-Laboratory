package repositories;

import datasource.EntityManagerFactoryCreator;
import model.Continent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class ContinentRepository {
    @PersistenceContext
    private final EntityManager em;

    public ContinentRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Continent continent) {
        em.persist(continent);
    }

    public List<Continent> findAll() {
        return (List<Continent>) em.createNamedQuery("Continent.findAll")
                .getResultList();
    }

    public Continent findById(Long id) {
        return (Continent) em.createNamedQuery("Continent.findById")
                .setParameter("id", id)
                .getSingleResult();
    }

    public Continent findByName(String name) {
        return (Continent) em.createNamedQuery("Continent.findByName")
                .setParameter("name", name)
                .getSingleResult();
    }

    public void remove(Continent continent) {
        em.remove(continent);
    }

    public void update() {
        em.getTransaction().commit();
    }
}
