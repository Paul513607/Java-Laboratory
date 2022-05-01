import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import datasource.EntityManagerFactoryCreator;
import model.*;
import repositories.CityRepository;
import repositories.ContinentRepository;
import repositories.CountryRepository;

public class Main {
    public void testJPA() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("CitiesPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Continent continent = new Continent("Europe");
        em.persist(continent);

        Continent c = (Continent) em.createQuery(
                        "select e from Continent e where e.name='Europe'")
                .getSingleResult();
        c.setName("Africa");
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    public void testJPAWithRepositories() {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        ContinentRepository continentRepo = new ContinentRepository(em);
        CountryRepository countryRepo = new CountryRepository(em);
        CityRepository cityRepo = new CityRepository(em);

        Continent continent = new Continent("Asia");
        continentRepo.create(continent);
        Country country = new Country("Japan", continent);
        countryRepo.create(country);
        City city = new City("Tokyo", country, false, 35.672855, 139.817413);
        cityRepo.create(city);

        City city1 = cityRepo.findByName("Tokyo");
        city1.setName("Kyoto");
        cityRepo.update();

        em.close();
        EntityManagerFactoryCreator.closeEntityManagerFactory();
    }

    public static void main(String[] args) {
        Main main = new Main();
        // main.testJPA();
        main.testJPAWithRepositories();
    }
}
