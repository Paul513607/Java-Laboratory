package solver;

import datasource.EntityManagerFactoryCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.City;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import repositories.CityRepository;
import repositories.CountryRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSolver {
    private Model model = new Model("Choco solver");

    public void solveModel(int lowerBound, int upperBound) {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();

        CityRepository cityRepo = new CityRepository(em);
        CountryRepository countryRepo = new CountryRepository(em);
        List<City> cities = new ArrayList<>((Collection<? extends City>) cityRepo.findAll());

        List<Character> citiesFirstLetters = new ArrayList<>();
        List<Integer> citiesPopulation = new ArrayList<>();
        List<String> citiesCountries = new ArrayList<>();
        List<Long> citiesIds = new ArrayList<>();

        for (City city : cities) {
            citiesFirstLetters.add(city.getName().charAt(0));
            citiesPopulation.add(city.getPopulation().intValue());
            citiesCountries.add(countryRepo.findCountryOfCity(city));
            citiesIds.add((city.getId()));
        }

        int[] citiesLettersArray1 = new int[citiesFirstLetters.size()];
        int[] citiesLettersArray2 = new int[citiesFirstLetters.size()];
        int length = 0;

        for (Character ch : citiesFirstLetters) {
            citiesLettersArray1[length] = ch;
            citiesLettersArray2[length++] = ch;
        }

        int[] cityPop1 = new int[citiesPopulation.size()];
        int[] cityPop2 = new int[citiesPopulation.size()];
        length = 0;

        for (Integer integer : citiesPopulation) {
            cityPop1[length] = integer;
            cityPop2[length++] = integer;
        }

        // We use ids and compare them in order for the model to have access to them
        int[] ids1 = new int[citiesIds.size()];
        int[] ids2 = new int[citiesIds.size()];
        length = 0;

        for (Long id : citiesIds) {
            ids1[length] = id.intValue();
            ids2[length++] = id.intValue();
        }

        IntVar firstLetter1 = model.intVar("firstLetter1", citiesLettersArray1);
        IntVar firstLetter2 = model.intVar("firstLetter2", citiesLettersArray2);

        IntVar population1 = model.intVar("population1", cityPop1);
        IntVar population2 = model.intVar("population2", cityPop2);

        IntVar id1 = model.intVar("id1", ids1);
        IntVar id2 = model.intVar("id2", ids2);

        model.allEqual(firstLetter1, firstLetter2).post();
        model.arithm(population1, "+", population2, ">=", lowerBound).post();
        model.arithm(population1, "+", population2, "<=", upperBound).post();
        model.arithm(id1, "-", id2, "!=", 0).post();

        Solver solver = model.getSolver();

        int solution = 0;
        while (solver.solve()) {
            City city1 = cityRepo.findById(((long) id1.getValue())).get();
            City city2 = cityRepo.findById(((long) id2.getValue())).get();

            String countryName1 = countryRepo.findCountryOfCity(city1);
            String countryName2 = countryRepo.findCountryOfCity(city2);

            if (!countryName1.equals(countryName2)) {
                solution++;
                System.out.println("Found solution " + solution + ": " + city1.getName() + ", " + city2.getName() +
                        "; populations: " + population1 + " " + population2 +
                        "; of countries: " + countryName1 + " " + countryName2);
                if (solution == 10)
                    break;
            }
        }

        em.close();
    }
}
