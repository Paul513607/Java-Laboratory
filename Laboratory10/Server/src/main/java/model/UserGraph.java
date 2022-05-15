package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGraph implements Graph<User, UserFriendship> {
    private Set<User> users = new HashSet<>();
    private Set<UserFriendship> friendships = new HashSet<>();

    @Override
    public Set<UserFriendship> getAllEdges(User user, User v1) {
        return friendships;
    }

    @Override
    public UserFriendship getEdge(User user, User v1) {
        for (UserFriendship friendship : friendships) {
            if (friendship.containsUser(user) && friendship.containsUser(v1)) {
                return friendship;
            }
        }
        return null;
    }

    @Override
    public Supplier<User> getVertexSupplier() {
        return null;
    }

    @Override
    public Supplier<UserFriendship> getEdgeSupplier() {
        return null;
    }

    @Override
    public UserFriendship addEdge(User user, User v1) {
        users.add(user);
        users.add(v1);
        UserFriendship friendship = new UserFriendship(user, v1);
        friendships.add(friendship);
        return friendship;
    }

    @Override
    public boolean addEdge(User user, User v1, UserFriendship userFriendship) {
        users.add(user);
        users.add(v1);
        userFriendship.setUser1(user);
        userFriendship.setUser2(v1);
        friendships.add(userFriendship);
        return true;
    }

    @Override
    public User addVertex() {
        return null;
    }

    @Override
    public boolean addVertex(User user) {
        users.add(user);
        return true;
    }

    @Override
    public boolean containsEdge(User user, User v1) {
        for (UserFriendship friendship : friendships) {
            if (friendship.containsUser(user) && friendship.containsUser(v1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(UserFriendship userFriendship) {
        return friendships.contains(userFriendship);
    }

    @Override
    public boolean containsVertex(User user) {
        return users.contains(user);
    }

    @Override
    public Set<UserFriendship> edgeSet() {
        return friendships;
    }

    @Override
    public int degreeOf(User user) {
        return 0;
    }

    @Override
    public Set<UserFriendship> edgesOf(User user) {
        Set<UserFriendship> tmpSet = new HashSet<>();
        for (UserFriendship friendship : friendships) {
            if (friendship.containsUser(user)) {
                tmpSet.add(friendship);
            }
        }
        return tmpSet;
    }

    @Override
    public int inDegreeOf(User user) {
        return 0;
    }

    @Override
    public Set<UserFriendship> incomingEdgesOf(User user) {
        Set<UserFriendship> tmpSet = new HashSet<>();
        for (UserFriendship friendship : friendships) {
            if (friendship.containsUser(user)) {
                tmpSet.add(friendship);
            }
        }
        return tmpSet;
    }

    @Override
    public int outDegreeOf(User user) {
        return 0;
    }

    @Override
    public Set<UserFriendship> outgoingEdgesOf(User user) {
        Set<UserFriendship> tmpSet = new HashSet<>();
        for (UserFriendship friendship : friendships) {
            if (friendship.containsUser(user)) {
                tmpSet.add(friendship);
            }
        }
        return tmpSet;
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
    public Set<UserFriendship> removeAllEdges(User user, User v1) {
        Set<UserFriendship> toRemove = new HashSet<>();
        for (UserFriendship friendship : friendships) {
            if (friendship.containsUser(user) && friendship.containsUser(v1)) {
                toRemove.add(friendship);
            }
        }
        friendships.removeAll(toRemove);
        return toRemove;
    }

    @Override
    public boolean removeAllVertices(Collection<? extends User> collection) {
        if (!users.containsAll(collection)) {
            return false;
        }
        users.removeAll(collection);
        return true;
    }

    @Override
    public UserFriendship removeEdge(User user, User v1) {
        UserFriendship toRemove = new UserFriendship();
        for (UserFriendship friendship : friendships) {
            if (friendship.containsUser(user) && friendship.containsUser(v1)) {
                toRemove = friendship;
                break;
            }
        }

        friendships.remove(toRemove);
        return toRemove;
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
    public boolean removeVertex(User user) {
        if (!users.contains(user)) {
            return false;
        }
        users.remove(user);
        return true;
    }

    @Override
    public Set<User> vertexSet() {
        return users;
    }

    @Override
    public User getEdgeSource(UserFriendship userFriendship) {
        return userFriendship.getUser1();
    }

    @Override
    public User getEdgeTarget(UserFriendship userFriendship) {
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
    public double getEdgeWeight(UserFriendship userFriendship) {
        return 0;
    }

    @Override
    public void setEdgeWeight(UserFriendship userFriendship, double v) {

    }
}
