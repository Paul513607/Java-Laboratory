package catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import customexceptions.SameIdItemExistsException;
import items.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// A catalog of items. Has methods like add(Item ) and toString.
public class Catalog implements Serializable {
    private String name;
    private List<Item> itemList = new ArrayList<>();

    public Catalog() {
    }

    public Catalog(String name) {
        this.name = name;
    }

    public Catalog(String name, List<Item> itemList) {
        this.name = name;
        this.itemList = itemList;
    }

    public void add(Item item) throws SameIdItemExistsException {
        Item item1 = itemList.stream()
                .filter(item2 -> item2.equals(item))
                .findFirst()
                .orElse(null);
        if (item1 != null)
            throw new SameIdItemExistsException(new Exception("An item with id: " + item.getId() + " already exists!"));
        else
            itemList.add(item);
    }

    public Item findItemById(String itemId) {
        return itemList.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "name='" + name + '\'' +
                ", itemList=" + itemList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(name, catalog.name) && Objects.equals(itemList, catalog.itemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, itemList);
    }
}
