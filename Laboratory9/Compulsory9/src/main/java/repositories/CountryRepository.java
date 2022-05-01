package repositories;

import datasource.EntityManagerFactoryCreator;
import model.Continent;
import model.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CountryRepository {
    @PersistenceContext
    private final EntityManager em;

    public CountryRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Country country) {
        em.persist(country);
    }

    public List<Country> findAll() {
        return (List<Country>) em.createNamedQuery("Country.findAll")
                .getResultList();
    }

    public Country findById(Long id) {
        return (Country) em.createNamedQuery("Country.findById")
                .setParameter("id", id)
                .getSingleResult();
    }

    public Country findByName(String name) {
        return (Country) em.createNamedQuery("Country.findByName")
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Country> findByContinent(Continent continent) {
        return (List<Country>) em.createNamedQuery("Country.findByContinent")
                .setParameter("continent", continent)
                .getResultList();
    }

    public void remove(Country country) {
        em.remove(country);
    }

    public void update() {
        em.getTransaction().commit();
    }
}
