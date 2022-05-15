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
public class RegisterCommand implements Command {
    private List<String> args = new ArrayList<>();

    private String output;

    private String errorMsg;
    private boolean statusCode;

    public RegisterCommand(List<String> args) {
        this.args = args;
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

        UserRepository userRepo = new UserRepository(em);
        Optional<User> userOpt = userRepo.findByName(args.get(1));
        if (userOpt.isPresent()) {
            statusCode = false;
            errorMsg = "A user with that name already exists.";
            return;
        }

        User user = new User(args.get(1));
        userRepo.save(user);
        statusCode = true;
        output = "Successful register";

        em.getTransaction().commit();
        em.close();
    }
}
