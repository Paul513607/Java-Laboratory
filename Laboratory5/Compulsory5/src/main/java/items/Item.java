package items;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.*;

/** The abstract class for an item in the catalog */
@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "book"),
        @JsonSubTypes.Type(value = Article.class, name = "article"),
})
public abstract class Item implements Serializable {
    protected String id;
    protected String title;
    protected String location;
    protected Map<String, Object> tags = new HashMap<>();
    protected Set<ClassificationType> classificationTypesSet = new HashSet<>();

    public Item() {
    }

    public Item(String id) {
        this.id = id;
    }

    public Item(String id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public Item(String id, String title, String location, Map<String, Object> tags) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<ClassificationType> getClassificationTypesSet() {
        return classificationTypesSet;
    }

    public void setClassificationTypesSet(Set<ClassificationType> classificationTypesSet) {
        this.classificationTypesSet = classificationTypesSet;
    }

    public Map<String, Object> getTags() {
        return tags;
    }

    public void addReference(String key, Object reference) {
        tags.put(key, reference);
    }

    public void removeReference(String key) {
        tags.remove(key);
    }

    public void addClassification(ClassificationType type) {
        classificationTypesSet.add(type);
    }

    public boolean containsClassification(ClassificationType type) {
        return classificationTypesSet.contains(type);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", tags=" + tags +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, location, tags);
    }
}
