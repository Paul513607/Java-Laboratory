package repositories;

import abstractrepos.ContinentRepo;
import abstractrepos.CountryRepo;
import datasource.EntityManagerFactoryCreator;
import model.Continent;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ContinentRepository extends AbstractRepository<Continent, Long> implements ContinentRepo {
    public ContinentRepository(EntityManager em) {
        super(em);
    }

    @Override
    @Transactional
    public void save(Continent entity) {
        em.persist(entity);
    }

    public List<Continent> findAll() {
        return (List<Continent>) em.createNamedQuery("Continent.findAll")
                .getResultList();
    }

    public Optional<Continent> findByName(String name) {
        return Optional.of((Continent) em.createNamedQuery("Continent.findByName")
                .setParameter(1, name)
                .getSingleResult());
    }



    public void update() {
        em.getTransaction().commit();
    }
}
