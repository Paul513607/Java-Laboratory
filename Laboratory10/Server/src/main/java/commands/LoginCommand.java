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
public class LoginCommand implements Command {
    private List<String> args = new ArrayList<>();

    private User currentUser;
    private String output;

    private String errorMsg;

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

    private boolean statusCode;

    public LoginCommand(List<String> args) {
        this.args = args;
    }

    @Override
    public void execute() {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        UserRepository userRepo = new UserRepository(em);
        Optional<User> userOpt = userRepo.findByName(args.get(1));

        if (userOpt.isEmpty()) {
            statusCode = false;
            errorMsg = "No user with that name exists.";
            return;
        }

        statusCode = true;
        currentUser = userOpt.get();
        output = "Successful login as " + currentUser.getName();

        em.getTransaction().commit();
        em.close();
    }
}
