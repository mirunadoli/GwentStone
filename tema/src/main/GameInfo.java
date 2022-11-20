package main;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;

public class GameInfo {
    private Player player1;
    private Player player2;
    private Player activePlayer;
    private GameTable gameTable;
    private GameInput gameInput;
    private int currentAction;
    private ArrayNode output;
    private Statistics stats;
    private int gameEnd;
    private int roundCount;


    public GameInfo() {

    }

    public GameInfo(final Player player1, final Player player2,
                    final GameTable gameTable, final GameInput gameInput,
                    final ArrayNode output, final Statistics stats) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameTable = gameTable;
        this.gameInput = gameInput;
        this.output = output;
        this.stats = stats;
        this.roundCount = 1;
    }

    public final Player getPlayer1() {
        return player1;
    }

    public final void setPlayer1(final Player player1) {
        this.player1 = player1;
    }

    public final Player getPlayer2() {
        return player2;
    }

    public final void setPlayer2(final Player player2) {
        this.player2 = player2;
    }

    public final GameTable getGameTable() {
        return gameTable;
    }

    public final void setGameTable(final GameTable gameTable) {
        this.gameTable = gameTable;
    }

    public final GameInput getGameInput() {
        return gameInput;
    }

    public final void setGameInput(final GameInput gameInput) {
        this.gameInput = gameInput;
    }


    public final int getCurrentAction() {
        return currentAction;
    }

    public final void setCurrentAction(final int currentAction) {
        this.currentAction = currentAction;
    }

    public final ArrayNode getOutput() {
        return output;
    }

    public final void setOutput(final ArrayNode output) {
        this.output = output;
    }

    public final Statistics getStats() {
        return stats;
    }

    public final void setStats(final Statistics stats) {
        this.stats = stats;
    }

    public final int getGameEnd() {
        return gameEnd;
    }

    public final void setGameEnd(final int gameEnd) {
        this.gameEnd = gameEnd;
    }

    public final int getRoundCount() {
        return roundCount;
    }

    public final void setRoundCount(final int roundCount) {
        this.roundCount = roundCount;
    }

    public final Player getActivePlayer() {
        return activePlayer;
    }

    public final void setActivePlayer(final Player activePlayer) {
        this.activePlayer = activePlayer;
    }
}
