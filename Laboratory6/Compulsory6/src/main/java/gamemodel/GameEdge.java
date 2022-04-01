package gamemodel;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/** Class for modeling an edge (stroked line) in the game. */
@Data
@NoArgsConstructor
public class GameEdge implements Serializable {
    private GameNode gameNode1;
    private GameNode gameNode2;

    public GameEdge(GameNode gameNode1, GameNode gameNode2) {
        this.gameNode1 = gameNode1;
        this.gameNode2 = gameNode2;
    }

    public void setGameNode1Data(boolean isUsed, PlayerName playerName) {
        gameNode1.setUsed(isUsed);
        gameNode1.setPlayerName(playerName);
    }

    public void setGameNode2Data(boolean isUsed, PlayerName playerName) {
        gameNode2.setUsed(isUsed);
        gameNode2.setPlayerName(playerName);
    }

    public boolean containsNode(GameNode node) {
        return gameNode1.equals(node) || gameNode2.equals(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameEdge gameEdge = (GameEdge) o;
        return Objects.equals(gameNode1, gameEdge.gameNode1) && Objects.equals(gameNode2, gameEdge.gameNode2) ||
                Objects.equals(gameNode1, gameEdge.gameNode2) && Objects.equals(gameNode2, gameEdge.gameNode1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameNode1, gameNode2);
    }
}
