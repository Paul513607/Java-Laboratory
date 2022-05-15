package repositories;

import model.Message;

import javax.persistence.EntityManager;
import java.util.List;

/** A class for querying the messages table. */
public class MessageRepository extends AbstractRepository<Message, Long> {
    public MessageRepository(EntityManager em) {
        super(em);
    }

    public List<Message> findAllForId(Long idTo) {
        return em.createNamedQuery("Message.findAllForId")
                .setParameter(1, idTo)
                .getResultList();
    }

    public void readMessages(List<Message> messages) {
        for (Message message : messages) {
            message.setWasRead(true);
            this.save(message);
        }
    }
}
