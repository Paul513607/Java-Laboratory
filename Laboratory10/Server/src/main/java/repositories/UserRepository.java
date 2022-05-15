package repositories;

import model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/** A class for querying the users table. */
public class UserRepository extends AbstractRepository<User, Long> {
    public UserRepository(EntityManager em) {
        super(em);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> userOpt;
        try {
            userOpt = Optional.of(em.createQuery("SELECT t FROM User t WHERE t.id = ?1", User.class)
                    .setParameter(1, id)
                    .getSingleResult()
            );
        } catch (NoResultException exp) {
            userOpt = Optional.empty();
        }
        return userOpt;
    }


    public Optional<User> findByName(String name) {
        Optional<User> userOpt;
        try {
            userOpt = Optional.of((User) em.createNamedQuery("User.findByName")
                    .setParameter(1, name)
                    .getSingleResult());
        } catch (NoResultException exp) {
            userOpt = Optional.empty();
        }
        return userOpt;
    }

    public List<User> findAllFriends(User user) {
        return em.createNamedQuery("User.findAllFriends")
                .setParameter(1, user.getId())
                .getResultList();
    }

    public void update() {
        em.getTransaction().commit();
    }
}
