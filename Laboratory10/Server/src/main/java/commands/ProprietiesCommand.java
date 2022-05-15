package commands;

import algorithms.ProprietiesAnalyzer;
import datasource.EntityManagerFactoryCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;
import model.UserFriendship;
import model.UserGraph;
import org.w3c.dom.Entity;
import repositories.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProprietiesCommand implements Command {
    private List<String> args = new ArrayList<>();

    private User currUser;
    private String output;

    private boolean statusCode;
    private String errorMsg;

    public ProprietiesCommand(List<String> args, User currUser) {
        this.args = args;
        this.currUser = currUser;
    }

    @Override
    public void execute() {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        UserRepository userRepo = new UserRepository(em);
        List<User> userList = userRepo.findAll();

        Set<User> users = new HashSet<>();
        Set<UserFriendship> friendships = new HashSet<>();

        for (User user : userList) {
            users.add(user);
            for (User friend : user.getUserFriends()) {
                friendships.add(new UserFriendship(user, friend));
                friendships.add(new UserFriendship(friend, user));
            }
        }

        UserGraph graph = new UserGraph(users, friendships);
        ProprietiesAnalyzer analyzer = new ProprietiesAnalyzer(graph);
        analyzer.runAlgorithm();

        statusCode = true;
        output = analyzer.getSolution();

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public boolean isStatusCode() {
        return statusCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String getOutput() {
        return output;
    }
}
