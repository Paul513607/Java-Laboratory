package factory;

import daos.CityDao;
import daos.ContinentDao;
import daos.CountryDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JdbcFactory implements AbstractFactory<ContinentDao, CountryDao, CityDao> {
    @Autowired
    private CityDao cityDao;
    @Autowired
    private CountryDao countryDao;
    @Autowired
    private ContinentDao continentDao;

    @Override
    public void createProducts() {
        this.continentDao = new ContinentDao();
        this.countryDao = new CountryDao();
        this.cityDao = new CityDao();
    }

    @Override
    public void createProducts(ContinentDao repo1, CountryDao repo2, CityDao repo3) {
        this.continentDao = repo1;
        this.countryDao = repo2;
        this.cityDao = repo3;
    }

    @Override
    public ContinentDao getContinentRepo() {
        return continentDao;
    }

    @Override
    public CountryDao getCountryRepo() {
        return countryDao;
    }

    @Override
    public CityDao getCityRepo() {
        return cityDao;
    }
}
