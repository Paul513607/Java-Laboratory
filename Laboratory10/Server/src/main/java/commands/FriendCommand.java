package commands;

import datasource.EntityManagerFactoryCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.User;
import repositories.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendCommand implements Command {
    private List<String> args = new ArrayList<>();

    private User currUser;
    private String output;

    private String errorMsg;
    private boolean statusCode;

    public FriendCommand(List<String> args, User currUser) {
        this.args = args;
        this.currUser = currUser;
    }

    @Override
    public String getOutput() {
        return output;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public boolean isStatusCode() {
        return statusCode;
    }

    @Override
    public void execute() {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        args.remove("friend");

        UserRepository userRepo = new UserRepository(em);
        for (String username : args) {
            Optional<User> nextUser = userRepo.findByName(username);
            nextUser.ifPresent(user -> currUser.getUserFriends().add(user));
        }
        userRepo.update(currUser);

        statusCode = true;
        output = "Successfully added friends";
        em.getTransaction().commit();
        em.close();
    }
}
