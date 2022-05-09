package repositories;

import abstractrepos.CountryRepo;
import datasource.EntityManagerFactoryCreator;
import model.City;
import model.Continent;
import model.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/** A class for querying the countries table. */
public class CountryRepository extends AbstractRepository<Country, Long> implements CountryRepo {
    public CountryRepository(EntityManager em) {
        super(em);
    }

    @Override
    @Transactional
    public void save(Country entity) {
        em.persist(entity);
    }

    public Optional<Country> findByName(String name) {
        return Optional.of((Country) em.createNamedQuery("Country.findByName")
                .setParameter(1, name)
                .getSingleResult());
    }

    public List<Country> findAll() {
        return (List<Country>) em.createNamedQuery("Country.findAll")
                .getResultList();
    }

    public String findCountryOfCity(City city) {
        return (String) em.createNamedQuery("Country.findCountryOfCity")
                .setParameter(1, city.getId())
                .getSingleResult();
    }

    public void update() {
        em.getTransaction().commit();
    }
}
