package components;

import cards.minionCards.MinionCard;

import java.util.ArrayList;

public class GameTable {
    private ArrayList<ArrayList<MinionCard>> rows = new ArrayList<>();
    private int[][] frozen = new int[Constants.MAX_ROWS][Constants.MAX_CARDS];
    private int[][] hasAttacked = new int[Constants.MAX_ROWS][Constants.MAX_CARDS];


    public GameTable() {
        for (int i = 0; i < Constants.MAX_ROWS; i++) {
            this.rows.add(new ArrayList<>(Constants.MAX_CARDS));
            for (int j = 0; j < Constants.MAX_CARDS; j++) {
                this.frozen[i][j] = 0;
                this.hasAttacked[i][j] = 0;
            }
        }
    }

    public GameTable(final GameTable gameTable) {
        this();
        for (int i = 0; i < Constants.MAX_ROWS; i++) {
            for (int j = 0; j < gameTable.rows.get(i).size(); j++) {
                this.rows.get(i).add(gameTable.rows.get(i).get(j));
                this.frozen[i][j] = gameTable.frozen[i][j];
                this.hasAttacked[i][j] = gameTable.hasAttacked[i][j];
            }
        }
    }

    public final ArrayList<ArrayList<MinionCard>> getRows() {
        return rows;
    }

    public final void setRows(final ArrayList<ArrayList<MinionCard>> rows) {
        this.rows = rows;
    }

    public final int[][] getFrozen() {
        return frozen;
    }

    public final void setFrozen(final int[][] frozen) {
        this.frozen = frozen;
    }

    /**
     *
     * @param x
     * @param y
     * @param val
     */
    public final void setFrozenElem(final int x, final int y, final int val) {
        this.frozen[x][y] = val;
    }

    public final int[][] getHasAttacked() {
        return hasAttacked;
    }

    public final void setHasAttacked(final int[][] hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     *
     * @param x
     * @param y
     * @param val
     */
    public final void setHasAttackedElem(final int x, final int y, final int val) {
        this.hasAttacked[x][y] = val;
    }
}
