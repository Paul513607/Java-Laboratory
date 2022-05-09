package daos;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** The interface dao for accessing a generic map object that has a name and an id from the database. */
public interface GenericDao<T> {
    void save(String name) throws SQLException;
    List<T> findAll() throws SQLException;
    Optional<T> findByName(String name) throws SQLException;
    Optional<T> findById(Long id) throws SQLException;
}
