package com.example.Server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGraph implements Graph<UserEntity, UserFriendship> {
    private Set<UserEntity> users = new HashSet<>();
    private Set<UserFriendship> friendships = new HashSet<>();


    @Override
    public Set<UserFriendship> getAllEdges(UserEntity userEntity, UserEntity v1) {
        return friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity) && userFriendship.containsUser(v1))
                .collect(Collectors.toSet());
    }

    @Override
    public UserFriendship getEdge(UserEntity userEntity, UserEntity v1) {
        return friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity) && userFriendship.containsUser(v1))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Supplier<UserEntity> getVertexSupplier() {
        return null;
    }

    @Override
    public Supplier<UserFriendship> getEdgeSupplier() {
        return null;
    }

    @Override
    public UserFriendship addEdge(UserEntity userEntity, UserEntity v1) {
        if (!users.contains(userEntity) || !users.contains(v1)) {
            return null;
        }

        UserFriendship newUserFriendship = new UserFriendship(userEntity, v1);
        friendships.add(newUserFriendship);
        return newUserFriendship;
    }

    @Override
    public boolean addEdge(UserEntity userEntity, UserEntity v1, UserFriendship userFriendship) {
        if (!users.contains(userEntity) || !users.contains(v1)) {
            return false;
        }
        friendships.add(new UserFriendship(userEntity, v1));
        return true;
    }

    @Override
    public UserEntity addVertex() {
        return null;
    }

    @Override
    public boolean addVertex(UserEntity userEntity) {
        users.add(userEntity);
        return true;
    }

    @Override
    public boolean containsEdge(UserEntity userEntity, UserEntity v1) {
        return (friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity) && userFriendship.containsUser(v1))
                .findFirst()
                .orElse(null) != null);
    }

    @Override
    public boolean containsEdge(UserFriendship userFriendship) {
        return friendships.contains(userFriendship);
    }

    @Override
    public boolean containsVertex(UserEntity userEntity) {
        return users.contains(userEntity);
    }

    @Override
    public Set<UserFriendship> edgeSet() {
        return friendships;
    }

    @Override
    public int degreeOf(UserEntity userEntity) {
        return 0;
    }

    @Override
    public Set<UserFriendship> edgesOf(UserEntity userEntity) {
        return friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public int inDegreeOf(UserEntity userEntity) {
        return 0;
    }

    @Override
    public Set<UserFriendship> incomingEdgesOf(UserEntity userEntity) {
        return friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public int outDegreeOf(UserEntity userEntity) {
        return 0;
    }

    @Override
    public Set<UserFriendship> outgoingEdgesOf(UserEntity userEntity) {
        return friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean removeAllEdges(Collection<? extends UserFriendship> collection) {
        if (!friendships.containsAll(collection)) {
            return false;
        }
        friendships.removeAll(collection);
        return true;
    }

    @Override
    public Set<UserFriendship> removeAllEdges(UserEntity userEntity, UserEntity v1) {
        Set<UserFriendship> toRemoveEdges = friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity) && userFriendship.containsUser(v1))
                .collect(Collectors.toSet());
        if (!toRemoveEdges.isEmpty()) {
            friendships.removeAll(toRemoveEdges);
        }

        return toRemoveEdges;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends UserEntity> collection) {
        if (!users.containsAll(collection)) {
            return false;
        }
        users.removeAll(collection);
        return true;
    }

    @Override
    public UserFriendship removeEdge(UserEntity userEntity, UserEntity v1) {
        UserFriendship toRemoveEdge = friendships.stream()
                .filter(userFriendship -> userFriendship.containsUser(userEntity) && userFriendship.containsUser(v1))
                .findFirst()
                .orElse(null);

        if (toRemoveEdge != null) {
            friendships.remove(toRemoveEdge);
        }

        return toRemoveEdge;
    }

    @Override
    public boolean removeEdge(UserFriendship userFriendship) {
        if (!friendships.contains(userFriendship)) {
            return false;
        }
        friendships.remove(userFriendship);
        return true;
    }

    @Override
    public boolean removeVertex(UserEntity userEntity) {
        if (!users.contains(userEntity)) {
            return false;
        }
        users.remove(userEntity);
        return true;
    }

    @Override
    public Set<UserEntity> vertexSet() {
        return users;
    }

    @Override
    public UserEntity getEdgeSource(UserFriendship userFriendship) {
        return userFriendship.getUser1();
    }

    @Override
    public UserEntity getEdgeTarget(UserFriendship userFriendship) {
        return userFriendship.getUser2();
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
                return this;
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
    public double getEdgeWeight(UserFriendship userFriendship) {
        return 0;
    }

    @Override
    public void setEdgeWeight(UserFriendship userFriendship, double v) {

    }
}
