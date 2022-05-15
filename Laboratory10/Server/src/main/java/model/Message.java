package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/** The model for a messages in the database. */
@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Message.findAllForId",
            query = "SELECT e FROM Message e WHERE e.idTo = ?1 AND e.wasRead = false"),
})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    private Long id;
    private Long idFrom;
    private Long idTo;
    private String content;
    private boolean wasRead = false;

    public Message(Long idFrom, Long idTo, String content) {
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.content = content;
        this.wasRead = false;
    }
}
