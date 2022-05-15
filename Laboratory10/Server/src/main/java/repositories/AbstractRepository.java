package repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Class which defines the generic methods of the repository.
 * @param <T> is the object's datatype
 * @param <ID> is the object id's datatype
 */
public abstract class AbstractRepository<T, ID> {
    @PersistenceContext
    protected final EntityManager em;
    private T object = (T) new Object();

    public AbstractRepository(EntityManager em) {
        this.em = em;
    }

    public long count() {
        return (long) em.createQuery("SELECT COUNT(*) FROM T t")
                .getSingleResult();
    }

    @Transactional
    public void delete(T entity) {
        em.remove(entity);
    }

    @Transactional
    public void deleteAll() {
        em.createQuery("DELETE FROM " + object.getClass().getName() + " t", object.getClass())
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
            em.createQuery("DELETE FROM " + object.getClass().getName() + " t WHERE t.id = ?1", object.getClass())
                    .setParameter(1, id)
                    .executeUpdate();
        }
    }

    public void deleteById(ID id) {
        em.createQuery("DELETE FROM " + object.getClass().getName() + " t WHERE t.id = ?1", object.getClass())
                .setParameter(1, id)
                .executeUpdate();
    }

    public boolean existsById(ID id) {
        return em.createQuery("SELECT t FROM " + object.getClass().getName() + " t WHERE t.id = ?1", object.getClass())
                .setParameter(1, id)
                .getSingleResult() != null;
    }

    public Iterable<T> findAll() {
        return (Iterable<T>) em.createQuery("SELECT t FROM " + object.getClass().getName() + " t", object.getClass())
                .getResultList();
    }

    public Iterable<T> findAllById(Iterable<ID> ids) {
        List<T> entities = new ArrayList<>();

        for (ID id : ids) {
            Object entity = em.createQuery("SELECT t FROM " + object.getClass().getName() + " t WHERE t.id = ?1", object.getClass())
                    .setParameter(1, id)
                    .getSingleResult();

            if (entity != null)
                entities.add((T) entity);
        }

        return entities;
    }

    public Optional<T> findById(ID id) {
        return Optional.of((T) em.createQuery("SELECT t FROM " + object.getClass().getName() + " t WHERE t.id = ?1", object.getClass())
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

    @Transactional
    public void update(T entity) {
        em.merge(entity);
    }
}