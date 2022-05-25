package com.example.Server.algorithms;

import com.example.Server.model.UserEntity;
import com.example.Server.model.UserFriendship;
import com.example.Server.model.UserGraph;
import lombok.Data;
import org.jgrapht.alg.connectivity.BlockCutpointGraph;

import java.util.Set;

/** Class that applies BlockCutpointGraph on the graph of users and friendships. */
@Data
public class CutpointsAlgorithm {
    private final UserGraph graph;
    private Set<UserEntity> resultSet;

    public CutpointsAlgorithm(UserGraph graph) {
        this.graph = graph;
    }

    public void runAlgorithm() {
        BlockCutpointGraph<UserEntity, UserFriendship> blockCutpointGraph =
                new BlockCutpointGraph<>(graph);

        resultSet = blockCutpointGraph.getCutpoints();
    }

    public void printSolution() {
        System.out.println("The set of very important friends in the network is:");
        resultSet.forEach(userEntity -> System.out.println(userEntity.getName() +
                " (number of friends: " + userEntity.getUserFriends().size() + ")"));
    }
}
