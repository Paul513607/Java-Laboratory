import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import datasource.Database;
import datasource.EntityManagerFactoryCreator;
import factory.AbstractFactory;
import factory.JdbcFactory;
import factory.JpaFactory;
import model.*;
import repositories.CityRepository;
import repositories.ContinentRepository;
import repositories.CountryRepository;
import solver.ProblemSolver;
import util.DatasetImporter;
import util.Timer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        continentRepo.save(continent);
        Country country = new Country("Japan", continent);
        continent.getCountries().add(country);
        countryRepo.save(country);

        City city = new City("Tokyo", 0L, false, 35.672855, 139.817413, 0L);
        cityRepo.save(city);
        country.getCities().add(city);

        Optional<City> optionalCity1 = cityRepo.findByName("Tokyo");
        City city1;
        if (optionalCity1.isPresent()) {
            city1 = optionalCity1.get();
            city1.setName("Kyoto");
            cityRepo.update();
        }

        em.close();
        EntityManagerFactoryCreator.closeEntityManagerFactory();
    }

    public void testImportData() {
        Timer timer = Timer.getInstance();
        timer.start();

        DatasetImporter importer = new DatasetImporter();
        importer.importDatasets();

        timer.stop();
        timer.showTimeTaken();
    }
    // Time taken: 1109.837651722s (seconds).

    public void testProblemSolver() {
        ProblemSolver problemSolver = new ProblemSolver();
        problemSolver.solveModel(10000, Integer.MAX_VALUE);
    }

    public void testAbstractFactory(String[] args) throws SQLException {
        AbstractFactory factory;
        EntityManager em = null;

        if (args[0].equals("jpa")) {
            em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
            factory = new JpaFactory(em);
            em.getTransaction().begin();
        } else if (args[0].equals("jdbc")) {
            factory = new JdbcFactory();
        } else {
            System.out.println("You need to specify either \"jpa\" or \"jdbc\"");
            return;
        }

        factory.createProducts();

        Continent continent = new Continent("TestContinent3");
        factory.getContinentRepo().save(continent);

        //Continent continent1 = factory.getContinentRepo().findByName("TestContinent").get();
        //System.out.println(continent1.getName() + " " + continent1.getId());


        // close the connection
        if (args[0].equals("jpa")) {
            em.getTransaction().commit();
            em.close();
            EntityManagerFactoryCreator.closeEntityManagerFactory();
        } else if (args[0].equals("jdbc")) {
            Database.getConnection().commit();
            Database.closeConnection();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        // main.testJPA();
        // main.testJPAWithRepositories();
        // main.testImportData();
        main.testProblemSolver();
        try {
            main.testAbstractFactory(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
