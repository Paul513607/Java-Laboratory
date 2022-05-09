package factory;

import datasource.EntityManagerFactoryCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.CityRepository;
import repositories.ContinentRepository;
import repositories.CountryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JpaFactory implements AbstractFactory<ContinentRepository, CountryRepository, CityRepository> {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ContinentRepository continentRepo;
    @Autowired
    private CountryRepository countryRepo;
    @Autowired
    private CityRepository cityRepo;

    public JpaFactory(EntityManager em) {
        this.em = em;
    }

    @Override
    public void createProducts() {
        continentRepo = new ContinentRepository(em);
        countryRepo = new CountryRepository(em);
        cityRepo = new CityRepository(em);
    }

    @Override
    public ContinentRepository getContinentRepo() {
        return continentRepo;
    }

    @Override
    public CountryRepository getCountryRepo() {
        return countryRepo;
    }

    @Override
    public CityRepository getCityRepo() {
        return cityRepo;
    }

    @Override
    public void createProducts(ContinentRepository repo1, CountryRepository repo2, CityRepository repo3) {
        this.continentRepo = repo1;
        this.countryRepo = repo2;
        this.cityRepo = repo3;
    }
}
