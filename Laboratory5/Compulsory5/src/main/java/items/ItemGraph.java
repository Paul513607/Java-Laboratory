package items;

import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import java.util.*;
import java.util.function.Supplier;

/** A general class of an Item list and ItemLink set graph. */
public class ItemGraph<V, E extends ItemLink> implements Graph<V, E> {
    private List<Item> itemList = new ArrayList<>();
    private List<ClassificationType> typeList = new ArrayList<>(List.of(ClassificationType.values()));
    private Set<ItemLink> itemLinkSet = new HashSet<>();

    public ItemGraph() {
    }

    public ItemGraph(List<Item> itemList) {
        this.itemList = itemList;
        addItemLinks();
    }

    /** Adds links between items with common ClassificationTypes. */
    private void addItemLinks() {
        for (Item item : itemList) {
            for (ClassificationType type : ClassificationType.values()) {
                if (item.containsClassification(type))
                    itemLinkSet.add(new ItemLink(item, type));
            }
        }
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<ClassificationType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<ClassificationType> typeList) {
        this.typeList = typeList;
    }

    public Set<ItemLink> getItemLinkSet() {
        return itemLinkSet;
    }

    public void setItemLinkSet(Set<ItemLink> itemLinkSet) {
        this.itemLinkSet = itemLinkSet;
    }

    @Override
    public String toString() {
        return "ItemGraph{" +
                "itemList=" + itemList +
                ", typeList=" + typeList +
                ", itemLinkSet=" + itemLinkSet +
                '}';
    }

    @Override
    public Set<E> getAllEdges(V v, V v1) {
        if (!(v instanceof Item) && !(v1 instanceof ClassificationType))
            return null;
        for (ItemLink itemLink : itemLinkSet)
            if (itemLink.getItem().equals(v) && itemLink.getType().equals(v1))
                return Set.of((E) itemLink);
        return null;
    }

    @Override
    public E getEdge(V v, V v1) {
        if (!(v instanceof Item) && !(v1 instanceof ClassificationType))
            return null;
        for (ItemLink itemLink : itemLinkSet)
            if (itemLink.getItem().equals(v) && itemLink.getType().equals(v1))
                return (E) itemLink;
        return null;
    }

    @Override
    public Supplier<V> getVertexSupplier() {
        return null;
    }

    @Override
    public Supplier<E> getEdgeSupplier() {
        return null;
    }

    @Override
    public E addEdge(V v, V v1) {
        return null;
    }

    @Override
    public boolean addEdge(V v, V v1, E e) {
        return false;
    }

    @Override
    public V addVertex() {
        return null;
    }

    @Override
    public boolean addVertex(V v) {
        return false;
    }

    @Override
    public boolean containsEdge(V v, V v1) {
        if (!(v instanceof Item item) && !(v1 instanceof ClassificationType type) && !itemList.contains(v) && !typeList.contains(v1))
            return false;
        for (ItemLink itemLink : itemLinkSet)
            if (itemLink.getItem().equals(v) && itemLink.getType().equals(v1))
                return true;
        return false;
    }

    @Override
    public boolean containsEdge(E e) {
        for (ItemLink itemLink : itemLinkSet)
            if (itemLink.equals(itemLink))
                return true;
        return false;
    }

    @Override
    public boolean containsVertex(V v) {
        if (v instanceof Item) {
            for (Item item : itemList)
                if (item.equals(v))
                    return true;
        }
        else if (v instanceof ClassificationType) {
            for (ClassificationType type : typeList)
                if (type.equals(v))
                    return true;
        }
        return false;
    }

    @Override
    public Set<E> edgeSet() {
        return (Set<E>) itemLinkSet;
    }

    @Override
    public int degreeOf(V v) {
        return 0;
    }

    @Override
    public Set<E> edgesOf(V v) {
        Set<E> edgeSet = new HashSet<>();
        if (v instanceof Item) {
            for (ItemLink itemLink : itemLinkSet)
                if (itemLink.getItem().equals(v))
                    edgeSet.add((E) itemLink);
        }
        else if (v instanceof ClassificationType) {
            for (ItemLink itemLink : itemLinkSet)
                if (itemLink.getType().equals(v))
                    edgeSet.add((E) itemLink);
        }
        return edgeSet;
    }

    @Override
    public int inDegreeOf(V v) {
        return 0;
    }

    @Override
    public Set<E> incomingEdgesOf(V v) {
        Set<E> edgeSet = new HashSet<>();
        if (v instanceof Item) {
            for (ItemLink itemLink : itemLinkSet)
                if (itemLink.getItem().equals(v))
                    edgeSet.add((E) itemLink);
        }
        else if (v instanceof ClassificationType) {
            for (ItemLink itemLink : itemLinkSet)
                if (itemLink.getType().equals(v))
                    edgeSet.add((E) itemLink);
        }
        return edgeSet;
    }

    @Override
    public int outDegreeOf(V v) {
        return 0;
    }

    @Override
    public Set<E> outgoingEdgesOf(V v) {
        Set<E> edgeSet = new HashSet<>();
        if (v instanceof Item) {
            for (ItemLink itemLink : itemLinkSet)
                if (itemLink.getItem().equals(v))
                    edgeSet.add((E) itemLink);
        }
        else if (v instanceof ClassificationType) {
            for (ItemLink itemLink : itemLinkSet)
                if (itemLink.getType().equals(v))
                    edgeSet.add((E) itemLink);
        }
        return edgeSet;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> collection) {
        return false;
    }

    @Override
    public Set<E> removeAllEdges(V v, V v1) {
        return null;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> collection) {
        return false;
    }

    @Override
    public E removeEdge(V v, V v1) {
        return null;
    }

    @Override
    public boolean removeEdge(E e) {
        return false;
    }

    @Override
    public boolean removeVertex(V v) {
        return false;
    }

    @Override
    public Set<V> vertexSet() {
        Set<Object> vertexSet = new HashSet<>(itemList);
        vertexSet.addAll(typeList);
        return (Set<V>) vertexSet;
    }

    @Override
    public V getEdgeSource(E e) {
        return (V) e.getItem();
    }

    @Override
    public V getEdgeTarget(E e) {
        return (V) e.getType();
    }

    @Override
    public GraphType getType() {
        return new GraphType() {
            @Override
            public boolean isDirected() {
                return false;
            }

            @Override
            public boolean isUndirected() {
                return true;
            }

            @Override
            public boolean isMixed() {
                return false;
            }

            @Override
            public boolean isAllowingMultipleEdges() {
                return false;
            }

            @Override
            public boolean isAllowingSelfLoops() {
                return false;
            }

            @Override
            public boolean isAllowingCycles() {
                return false;
            }

            @Override
            public boolean isWeighted() {
                return false;
            }

            @Override
            public boolean isSimple() {
                return false;
            }

            @Override
            public boolean isPseudograph() {
                return false;
            }

            @Override
            public boolean isMultigraph() {
                return false;
            }

            @Override
            public boolean isModifiable() {
                return false;
            }

            @Override
            public GraphType asDirected() {
                return null;
            }

            @Override
            public GraphType asUndirected() {
                return null;
            }

            @Override
            public GraphType asMixed() {
                return null;
            }

            @Override
            public GraphType asUnweighted() {
                return null;
            }

            @Override
            public GraphType asWeighted() {
                return null;
            }

            @Override
            public GraphType asModifiable() {
                return null;
            }

            @Override
            public GraphType asUnmodifiable() {
                return null;
            }
        }; // isUndirected
    }

    @Override
    public double getEdgeWeight(E e) {
        return 0;
    }

    @Override
    public void setEdgeWeight(E e, double v) {
        return;
    }

    public void printGraph() {
        System.out.println("Items:");
        for (Item item : itemList)
            System.out.println(item.getId() + ": " + item.getClassificationTypesSet());
        System.out.println("\nLinks:");
        for (ItemLink itemLink : itemLinkSet)
            System.out.println(itemLink.getItem().getId() + " -> " + itemLink.getType());
    }

    public Set<ClassificationType> itemLinks(Item item) {
        Set<ClassificationType> typeSet = new HashSet<>();
        for (ItemLink itemLink : itemLinkSet)
            if (itemLink.getItem().equals(item))
                typeSet.add(itemLink.getType());
        return typeSet;
    }

    public Set<Item> typeLinks(ClassificationType type) {
        Set<Item> itemSet = new HashSet<>();
        for (ItemLink itemLink : itemLinkSet)
            if (itemLink.getType().equals(type))
                itemSet.add(itemLink.getItem());
        return itemSet;
    }

    public int getNumberOfNodes() {
        return itemList.size() + typeList.size();
    }
}
