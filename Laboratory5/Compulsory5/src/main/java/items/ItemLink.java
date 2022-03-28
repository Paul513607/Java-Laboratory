package items;

import java.util.Objects;
import java.util.Set;

public class ItemLink {
    private Item item;
    private ClassificationType type;

    public ItemLink() {
    }

    public ItemLink(Item item, ClassificationType type) {
        this.item = item;
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ClassificationType getType() {
        return type;
    }

    public void setType(ClassificationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ItemLink{" +
                "item1=" + item +
                ", classificationType=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemLink itemLink = (ItemLink) o;
        return Objects.equals(item, itemLink.item) && type == itemLink.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, type);
    }
}
