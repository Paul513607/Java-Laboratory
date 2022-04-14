package game.prefixtreeutil;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A node in the prefix tree dictionary. */
@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
public class PrefixTreeNode implements Serializable {
    private String content;
    boolean isWord;

    public PrefixTreeNode(String content) {
        this.content = content;
        this.isWord = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrefixTreeNode that = (PrefixTreeNode) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
