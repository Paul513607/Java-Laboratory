package game.prefixtreeutil;

import lombok.*;

@Getter(onMethod_ = {@Synchronized})
@Setter(onMethod_ = {@Synchronized})
@ToString
@EqualsAndHashCode
public class PrefixTreeEdge {
    private PrefixTreeNode parent;
    private PrefixTreeNode child;
    private Character letter;

    public PrefixTreeEdge(PrefixTreeNode parent, PrefixTreeNode child, Character letter) {
        this.parent = parent;
        this.child = child;
        this.letter = letter;
    }
}
