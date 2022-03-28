package gamemodel;

import java.util.Objects;

public class GameNode {
    private String id;
    private boolean isUsed = false;
    private PlayerName playerName = null;
    private double xCoord;
    private double yCoord;

    public GameNode() {
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public PlayerName getPlayerName() {
        return playerName;
    }

    public void setPlayerName(PlayerName playerName) {
        this.playerName = playerName;
    }

    public double getxCoord() {
        return xCoord;
    }

    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    public double getyCoord() {
        return yCoord;
    }

    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    @Override
    public String toString() {
        return "GameNode{" +
                "id='" + id + '\'' +
                ", isUsed=" + isUsed +
                ", playerName=" + playerName +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameNode gameNode = (GameNode) o;
        return Objects.equals(id, gameNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isUsed, playerName);
    }

    public void takeNode(PlayerName playerName) {
        this.isUsed = true;
        this.playerName = playerName;
    }

    public double getDistanceTo(GameNode gameNode) {
        return Math.sqrt((xCoord - gameNode.getxCoord()) * (xCoord - gameNode.getxCoord()) +
                (yCoord - gameNode.getyCoord()) * (yCoord - gameNode.getyCoord()));
    }
}
