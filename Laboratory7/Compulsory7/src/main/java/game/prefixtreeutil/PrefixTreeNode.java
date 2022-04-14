package game.prefixtreeutil;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
public class PrefixTreeNode {
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
