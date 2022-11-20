package commands;

import components.GameInfo;

public class Statistics {

    private int gPlayed;
    private int pOneWins;
    private int pTwoWins;

    public Statistics() {
        this.gPlayed = 0;
        this.pOneWins = 0;
        this.pTwoWins = 0;
    }

    /**
     *
     * @return
     */
    public int getgPlayed() {
        return gPlayed;
    }

    /**
     *
     * @param gPlayed
     */
    public void setgPlayed(final int gPlayed) {
        this.gPlayed = gPlayed;
    }

    /**
     *
     * @return
     */
    public int getpOneWins() {
        return pOneWins;
    }

    /**
     *
     * @param pOneWins
     */
    public void setpOneWins(final int pOneWins) {
        this.pOneWins = pOneWins;
    }

    /**
     *
     * @return
     */
    public int getpTwoWins() {
        return pTwoWins;
    }

    /**
     *
     * @param pTwoWins
     */
    public void setpTwoWins(final int pTwoWins) {
        this.pTwoWins = pTwoWins;
    }

    /**
     * get first player's wins
     * @param game
     */
    public final void getPlayerOneWins(final GameInfo game) {
        game.getOutput().addObject().put("command", "getPlayerOneWins")
                .put("output", pOneWins);
    }

    /**
     * get second player's wins
     * @param game
     */
    public final void getPlayerTwoWins(final GameInfo game) {
        game.getOutput().addObject().put("command", "getPlayerTwoWins")
                .put("output", pTwoWins);
    }

    /**
     * get all the games played
     * @param game
     */
    public final void getTotalGamesPlayed(final GameInfo game) {

        game.getOutput().addObject().put("command", "getTotalGamesPlayed")
                .put("output", gPlayed);
    }
}
