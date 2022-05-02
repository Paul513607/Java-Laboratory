package model;

import dao.CityDao;
import dao.CitySisterRelationDao;
import dao.CountryDao;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/** The class representing the cities (nodes) and the city sister relations (edges) between them, as a graph. */
@Data
@NoArgsConstructor
public class CityRelationsGraph<V extends City, E extends CitySisterRelation> implements Graph<V, E> {
    private final CityDao cityDao = new CityDao();
    private final CitySisterRelationDao relationDao = new CitySisterRelationDao();

    private Set<City> fakeCities = new HashSet<>();
    private Set<CitySisterRelation> sisterRelations = new HashSet<>();

    public void initializeGraph() throws SQLException {
        sisterRelations = new HashSet<>(relationDao.findAll());

        for (CitySisterRelation relation : sisterRelations) {
            City city1 = cityDao.findCityById(relation.getCityId1());
            City city2 = cityDao.findCityById(relation.getCityId2());

            fakeCities.add(city1);
            fakeCities.add(city2);
        }
    }

    @Override
    public Set<E> getAllEdges(V v, V v1) {
        return (Set<E>) sisterRelations;
    }

    @Override
    public E getEdge(V v, V v1) {
        for (CitySisterRelation relation : sisterRelations) {
            if (relation.containsNode(v) && relation.containsNode(v1))
                return (E) relation;
        }
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
        if (!fakeCities.contains(v) || !fakeCities.contains(v1))
            return null;

        CitySisterRelation relation = new CitySisterRelation(v.getId(), v1.getId());
        try {
            relationDao.create(v.getId(), v1.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sisterRelations.add(relation);
        return (E) relation;
    }

    @Override
    public boolean addEdge(V v, V v1, E edge) {
        if (!fakeCities.contains(v) || !fakeCities.contains(v1))
            return false;

        edge = (E) new CitySisterRelation(v.getId(), v1.getId());
        try {
            relationDao.create(v.getId(), v1.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sisterRelations.add(edge);
        return true;
    }

    @Override
    public V addVertex() {
        return null;
    }

    @Override
    public boolean addVertex(V v) {
        CountryDao countryDao = new CountryDao();

        try {
            cityDao.create(v.getName(), countryDao.findByName("Imagination Land"), v.isCapital(), v.getLatitude(), v.getLongitude());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean containsEdge(V v, V v1) {
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v) && relation.containsNode(v1))
                return true;
        return false;
    }

    @Override
    public boolean containsEdge(E e) {
        return sisterRelations.contains(e);
    }

    @Override
    public boolean containsVertex(V v) {
        return fakeCities.contains(v);
    }

    @Override
    public Set<E> edgeSet() {
        return (Set<E>) sisterRelations;
    }

    @Override
    public int degreeOf(V v) {
        int degree = 0;
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v))
                degree++;
        return degree;
    }

    @Override
    public Set<E> edgesOf(V v) {
        Set<E> edges = new HashSet<>();
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v))
                edges.add((E) relation);
        return edges;
    }

    @Override
    public int inDegreeOf(V v) {
        int degree = 0;
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v))
                degree++;
        return degree;
    }

    @Override
    public Set<E> incomingEdgesOf(V v) {
        Set<E> edges = new HashSet<>();
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v))
                edges.add((E) relation);
        return edges;
    }

    @Override
    public int outDegreeOf(V v) {
        int degree = 0;
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v))
                degree++;
        return degree;
    }

    @Override
    public Set<E> outgoingEdgesOf(V v) {
        Set<E> edges = new HashSet<>();
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v))
                edges.add((E) relation);
        return edges;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends E> collection) {
        if (!sisterRelations.containsAll(collection))
            return false;
        sisterRelations.removeAll(collection);
        return true;
    }

    @Override
    public Set<E> removeAllEdges(V v, V v1) {
        Set<E> edgesToRemove = new HashSet<>();
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v) && relation.containsNode(v1))
                edgesToRemove.add((E) relation);
        sisterRelations.removeAll(edgesToRemove);
        return edgesToRemove;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends V> collection) {
        if (!fakeCities.containsAll(collection))
            return false;
        fakeCities.removeAll(collection);
        return true;
    }

    @Override
    public E removeEdge(V v, V v1) {
        E edgeToRemove = null;
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v) && relation.containsNode(v1)) {
                edgeToRemove = (E) relation;
                break;
            }

        if (edgeToRemove != null)
            sisterRelations.remove(edgeToRemove);
        return edgeToRemove;
    }

    @Override
    public boolean removeEdge(E e) {
        if (!sisterRelations.contains(e))
            return false;
        sisterRelations.remove(e);
        return true;
    }

    @Override
    public boolean removeVertex(V v) {
        if (!fakeCities.contains(v))
            return false;
        fakeCities.remove(v);

        Set<E> edgesToRemove = new HashSet<>();
        for (CitySisterRelation relation : sisterRelations)
            if (relation.containsNode(v))
                edgesToRemove.add((E) relation);
        sisterRelations.removeAll(edgesToRemove);

        return true;
    }

    @Override
    public Set<V> vertexSet() {
        return (Set<V>) fakeCities;
    }

    @Override
    public V getEdgeSource(E e) {
        try {
            return (V) cityDao.findCityById(e.getCityId1());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public V getEdgeTarget(E e) {
        try {
            return (V) cityDao.findCityById(e.getCityId2());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
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
                return true;
            }

            @Override
            public boolean isWeighted() {
                return false;
            }

            @Override
            public boolean isSimple() {
                return true;
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
                return this;
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
        };
    }

    @Override
    public double getEdgeWeight(E e) {
        return 0;
    }

    @Override
    public void setEdgeWeight(E e, double v) {
    }
}
