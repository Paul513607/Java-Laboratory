package repositories;

import abstractrepos.CityRepo;
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

public class CityRepository extends AbstractRepository<City, Long> implements CityRepo {
    public CityRepository(EntityManager em) {
        super(em);
    }

    @Override
    @Transactional
    public void save(City entity) {
        em.persist(entity);
    }

    public Optional<City> findByName(String name) {
        return Optional.of((City) em.createNamedQuery("City.findByName")
                .setParameter(1, name)
                .getSingleResult());
    }

    public List<City> findAll() {
        return (List<City>) em.createNamedQuery("City.findAll")
                .getResultList();
    }

    public void update() {
        em.getTransaction().commit();
    }
}