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
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadCommand implements Command {
    private List<String> args = new ArrayList<>();

    private User currUser;
    private String output;

    private String errorMsg;
    private boolean statusCode;

    public ReadCommand(List<String> args, User currUser) {
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

        List<Message> userMessages = msgRepo.findAllForId(currUser.getId());
        StringBuilder msgBuilder = new StringBuilder();
        for(Message msg : userMessages) {
            Optional<User> userOpt = userRepo.findById(msg.getIdFrom());
            if (userOpt.isPresent()) {
                String nextMsg = userOpt.get().getName() + ": " + msg.getContent() + '|';
                msgBuilder.append(nextMsg);
                msg.setWasRead(true);
                msgRepo.update(msg);
            }
        }

        statusCode = true;
        if(msgBuilder.isEmpty()) {
            output = "No unread messages";
        }
        else {
            output = msgBuilder.toString();
        }

        em.getTransaction().commit();
        em.close();
    }
}
