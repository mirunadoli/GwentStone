package main.commands;

import cards.Card;
import cards.heroCards.HeroCard;
import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import main.GameInfo;
import main.GameTable;

import java.util.ArrayList;

public class Debugger {
    /**
     *
     * @param game
     */
    public void getPlayerTurn(final GameInfo game) {
        if (game.getActivePlayer() == game.getPlayer1()) {
            game.getOutput().addObject().put("command", "getPlayerTurn").put("output", 1);
        } else {
            game.getOutput().addObject().put("command", "getPlayerTurn").put("output", 2);
        }
    }

    /**
     *
     * @param game
     * @param action
     */
    public void getPlayerDeck(final GameInfo game, final ActionsInput action) {

        if (action.getPlayerIdx() == 1) {
            game.getOutput().addObject().put("command", "getPlayerDeck")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", game.getPlayer1().getDeck());
        } else if (action.getPlayerIdx() == 2) {
            game.getOutput().addObject().put("command", "getPlayerDeck")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", game.getPlayer2().getDeck());
        }
    }

    /**
     *
     * @param game
     * @param action
     */
    public void getPlayerHero(final GameInfo game, final ActionsInput action) {

        HeroCard hero;
        if (action.getPlayerIdx() == 1) {
            hero = new HeroCard(game.getPlayer1().getHero());
            game.getOutput().addObject().put("command", "getPlayerHero")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", hero);
        } else {
            hero = new HeroCard(game.getPlayer2().getHero());
            game.getOutput().addObject().put("command", "getPlayerHero")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", hero);
        }

    }

    /**
     *
     * @param game
     * @param action
     */
    public void getPlayerMana(final GameInfo game, final ActionsInput action) {
        if (action.getPlayerIdx() == 1) {
            game.getOutput().addObject().put("command", "getPlayerMana")
                    .put("playerIdx", action.getPlayerIdx())
                    .put("output", game.getPlayer1().getMana());
        } else {
            game.getOutput().addObject().put("command", "getPlayerMana")
                    .put("playerIdx", action.getPlayerIdx())
                    .put("output", game.getPlayer2().getMana());
        }
    }

    /**
     *
     * @param game
     * @param action
     */
    public void getCardsInHand(final GameInfo game, final ActionsInput action) {
        ArrayList<Card> hand = new ArrayList<>();
        if (action.getPlayerIdx() == 1) {
            hand.addAll(game.getPlayer1().getHandCards());
            game.getOutput().addObject().put("command", "getCardsInHand")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", hand);
        } else {
            hand.addAll(game.getPlayer2().getHandCards());
            game.getOutput().addObject().put("command", "getCardsInHand")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", hand);
        }
    }

    /**
     *
     * @param game
     */
    public void getCardsOnTable(final GameInfo game) {
        // copie a mesei curente
        GameTable tableCopy = new GameTable(game.getGameTable());

        game.getOutput().addObject().put("command", "getCardsOnTable")
                .putPOJO("output", tableCopy.getRows());
    }

    /**
     *
     * @param game
     * @param action
     */
    public void getCardAtPosition(final GameInfo game, final ActionsInput action) {
        // se verifica daca exista carte la pozitia data
        int x = action.getX();
        int y = action.getY();

        if (x > 3 || y >= game.getGameTable().getRows().get(x).size()) {
            game.getOutput().addObject().put("command", "getCardAtPosition")
                    .put("output", "No card at that position.");
            return;
        }

        MinionCard card = new MinionCard(game.getGameTable().getRows().get(x).get(y));
        game.getOutput().addObject().put("command", "getCardAtPosition")
                .putPOJO("output", card);

    }

    /**
     *
     * @param game
     * @param action
     */
    public void getEnvironmentCardsInHand(final GameInfo game, final ActionsInput action) {
        ArrayList<Card> env = new ArrayList<>();

        if (action.getPlayerIdx() == 1) {
            env.addAll(game.getPlayer1().getHandEnv());
            game.getOutput().addObject().put("command", "getEnvironmentCardsInHand")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", env);
        } else if (action.getPlayerIdx() == 2) {
            env.addAll(game.getPlayer2().getHandEnv());
            game.getOutput().addObject().put("command", "getEnvironmentCardsInHand")
                    .put("playerIdx", action.getPlayerIdx())
                    .putPOJO("output", env);
        }
    }

    /**
     *
     * @param game
     */
    public void getFrozenCardsOnTable(final GameInfo game) {
        ArrayList<MinionCard> frozen = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (game.getGameTable().getFrozen()[i][j] == 1) {
                    frozen.add(game.getGameTable().getRows().get(i).get(j));
                }
            }
        }

        game.getOutput().addObject().put("command", "getFrozenCardsOnTable")
                .putPOJO("output", frozen);
    }
}
