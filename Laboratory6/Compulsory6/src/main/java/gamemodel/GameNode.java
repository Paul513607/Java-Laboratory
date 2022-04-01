package gamemodel;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/** Class for modeling a node (stone, circle) in the game. */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GameNode implements Serializable {
    private String id;
    private boolean isUsed = false;
    private PlayerName playerName = null;
    private double xCoord;
    private double yCoord;

    public GameNode(String id, double xCoord, double yCoord) {
        this.id = id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public GameNode(String id, boolean isUsed, PlayerName playerName, double xCoord, double yCoord) {
        this.id = id;
        this.isUsed = isUsed;
        this.playerName = playerName;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public GameNode(GameNode gameNode) {
        if (gameNode != null) {
            this.id = gameNode.id;
            this.isUsed = gameNode.isUsed;
            this.playerName = gameNode.playerName;
            this.xCoord = gameNode.xCoord;
            this.yCoord = gameNode.yCoord;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameNode gameNode = (GameNode) o;
        return Double.compare(gameNode.xCoord, xCoord) == 0 && Double.compare(gameNode.yCoord, yCoord) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isUsed, playerName, xCoord, yCoord);
    }

    public void takeNode(PlayerName playerName) {
        this.isUsed = true;
        this.playerName = playerName;
    }

    public double getDistanceTo(GameNode gameNode) {
        return Math.sqrt((xCoord - gameNode.getXCoord()) * (xCoord - gameNode.getXCoord()) +
                (yCoord - gameNode.getYCoord()) * (yCoord - gameNode.getYCoord()));
    }
}
