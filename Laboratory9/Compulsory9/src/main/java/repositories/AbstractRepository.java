package repositories;

import datasource.EntityManagerFactoryCreator;
import model.City;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T, ID> {
    @PersistenceContext
    protected final EntityManager em;

    public AbstractRepository(EntityManager em) {
        this.em = em;
    }

    public long count() {
        return (long) em.createNamedQuery("SELECT COUNT(*) FROM T")
                .getSingleResult();
    }

    @Transactional
    public void delete(T entity) {
        em.remove(entity);
    }

    @Transactional
    public void deleteAll() {
        em.createNamedQuery("DELETE FROM T")
                .executeUpdate();
    }

    @Transactional
    public void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            em.remove(entity);
        }
    }

    @Transactional
    public void deleteAllById(Iterable<? extends ID> ids) {
        for (ID id : ids) {
            em.createNamedQuery("DELETE FROM T t WHERE t.id = ?1")
                    .setParameter(1, id)
                    .executeUpdate();
        }
    }

    public void deleteById(ID id) {
        em.createNamedQuery("DELETE FROM T t WHERE t.id = ?1")
                .setParameter(1, id)
                .executeUpdate();
    }

    public boolean existsById(ID id) {
        return em.createNamedQuery("SELECT t FROM T t WHERE t.id = ?1")
                .setParameter(1, id)
                .getSingleResult() != null;
    }

    public Iterable<T> findAll() {
        return (Iterable<T>) em.createQuery("SELECT t FROM City t", City.class)
                .getResultList();
    }

    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> entities = new ArrayList<>();

        for (ID id : ids) {
            Object entity = em.createNamedQuery("SELECT t FROM T t WHERE t.id = ?1")
                    .setParameter(1, id)
                    .getSingleResult();

            if (entity != null)
                entities.add((T) entity);
        }

        return entities;
    }

    public Optional<T> findById(ID id) {
        return Optional.of((T) em.createQuery("SELECT t FROM City t WHERE t.id = ?1", City.class)
                .setParameter(1, id)
                .getSingleResult()
        );
    }

    @Transactional
    public void save(T entity) {
        em.persist(entity);
    }

    @Transactional
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            em.persist(entity);
        }

        return entities;
    }
}
