package factory;

import abstractrepos.CityRepo;
import abstractrepos.ContinentRepo;
import abstractrepos.CountryRepo;
import model.Continent;
import repositories.CityRepository;
import repositories.ContinentRepository;
import repositories.CountryRepository;

/** A class which will define whether we use a JPA or JDBC factory implementation. */
public interface AbstractFactory<T1 extends ContinentRepo, T2 extends CountryRepo, T3 extends CityRepo> {
    void createProducts();
    void createProducts(T1 repo1, T2 repo2, T3 repo3);
    T1 getContinentRepo();
    T2 getCountryRepo();
    T3 getCityRepo();
}
