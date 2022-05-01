package repositories;

import datasource.EntityManagerFactoryCreator;
import model.City;
import model.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CityRepository {
    @PersistenceContext
    private final EntityManager em;

    public CityRepository(EntityManager em) {
        this.em = em;
    }

    public void create(City city) {
        em.persist(city);
    }

    public List<City> findAll() {
        return (List<City>) em.createNamedQuery("City.findAll")
                .getResultList();
    }

    public City findById(Long id) {
        return (City) em.createNamedQuery("City.findById")
                .setParameter("id", id)
                .getSingleResult();
    }

    public City findByName(String name) {
        return (City) em.createNamedQuery("City.findByName")
                .setParameter(1, name)
                .getSingleResult();
    }

    public List<City> findByCountry(Country country) {
        return (List<City>) em.createNamedQuery("City.findByCountry")
                .setParameter("country", country)
                .getResultList();
    }

    public void remove(City city) {
        em.remove(city);
    }

    public void update() {
        em.getTransaction().commit();
    }
}