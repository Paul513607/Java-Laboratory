package game.prefixtreeutil;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.io.Serializable;

/** An edge in the prefix tree dictionary. */
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
public class PrefixTreeEdge implements Serializable {
    private PrefixTreeNode parent;
    private PrefixTreeNode child;
    private Character letter;

    public PrefixTreeEdge(PrefixTreeNode parent, PrefixTreeNode child, Character letter) {
        this.parent = parent;
        this.child = child;
        this.letter = letter;
    }
}
