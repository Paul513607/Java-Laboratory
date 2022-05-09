package abstractrepos;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** A generic repo for both the JPA and the JDBC implementations. */
public interface GenericRepo<T> {
    void save(T object) throws SQLException;
    List<T> findAll() throws SQLException;
    Optional<T> findById(Long id) throws SQLException;
    Optional<T> findByName(String name) throws SQLException;
}
