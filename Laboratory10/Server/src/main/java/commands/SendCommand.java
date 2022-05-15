package commands;

import datasource.EntityManagerFactoryCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Message;
import model.User;
import repositories.MessageRepository;
import repositories.UserRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendCommand implements Command {
    private List<String> args = new ArrayList<>();

    private User currUser;
    private String output;

    private String errorMsg;
    private boolean statusCode;

    public SendCommand(List<String> args, User currUser) {
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

        UserRepository userRepo = new UserRepository(em);
        MessageRepository msgRepo = new MessageRepository(em);

        List<User> userFriends = userRepo.findAllFriends(currUser);
        System.out.println("HERE " + userFriends);
        for (User user : userFriends) {
            Message nextMsg = new Message(currUser.getId(), user.getId(), args.get(1));
            msgRepo.save(nextMsg);
        }

        statusCode = true;
        output = "Successfully sent messages";
        em.getTransaction().commit();
        em.close();
    }
}
