package datasource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityManagerFactoryCreator {
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            createEntityManagerFactory();
        }

        return emf;
    }

    private static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("CitiesPU");
    }

    public static void closeEntityManagerFactory() {
        emf.close();
    }
}
