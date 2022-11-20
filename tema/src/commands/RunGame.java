package commands;

import components.Constants;
import fileio.ActionsInput;
import components.GameInfo;
import components.Player;

import java.util.ArrayList;

public class RunGame {
    //static int roundCount;
    private final Debugger debug = new Debugger();
    private final TableCommands table = new TableCommands();

    /**
     * calls the right methods based on the command received
     * @param game
     * @param action
     */
    void executeCommand(final GameInfo game, final ActionsInput action) {
        if (action.getCommand().equals("getPlayerDeck")) {
            debug.getPlayerDeck(game, action);
        } else if (action.getCommand().equals("getPlayerHero")) {
            debug.getPlayerHero(game, action);
        } else if (action.getCommand().equals("getPlayerTurn")) {
            debug.getPlayerTurn(game);
        } else if (action.getCommand().equals("getPlayerMana")) {
            debug.getPlayerMana(game, action);
        } else if (action.getCommand().equals("getCardsInHand")) {
            debug.getCardsInHand(game, action);
        } else if (action.getCommand().equals("placeCard")) {
            table.placeCard(game, action);
        } else if (action.getCommand().equals("getCardsOnTable")) {
            debug.getCardsOnTable(game);
        } else if (action.getCommand().equals("getCardAtPosition")) {
            debug.getCardAtPosition(game, action);
        } else if (action.getCommand().equals("getEnvironmentCardsInHand")) {
            debug.getEnvironmentCardsInHand(game, action);
        } else if (action.getCommand().equals("useEnvironmentCard")) {
            table.useEnvironmentCard(game, action);
        } else if (action.getCommand().equals("getFrozenCardsOnTable")) {
            debug.getFrozenCardsOnTable(game);
        } else if (action.getCommand().equals("cardUsesAttack")) {
            table.cardUsesAttack(game, action);
        } else if (action.getCommand().equals("cardUsesAbility")) {
            table.cardUsesAbility(game, action);
        } else if (action.getCommand().equals("useAttackHero")) {
            table.useAttackHero(game, action);
        } else if (action.getCommand().equals("useHeroAbility")) {
            table.useHeroAbility(game, action);
        } else if (action.getCommand().equals("getPlayerOneWins")) {
            game.getStats().getPlayerOneWins(game);
        } else if (action.getCommand().equals("getPlayerTwoWins")) {
            game.getStats().getPlayerTwoWins(game);
        } else if (action.getCommand().equals("getTotalGamesPlayed")) {
            game.getStats().getTotalGamesPlayed(game);
    }
}

    /**
     * executes a round
     * @param game
     */
    void executeRound(final GameInfo game) {

        // if starting player is first player, executes first player's turn
        // and then second player's turn
        if (game.getGameInput().getStartGame().getStartingPlayer() == 1) {
            game.setActivePlayer(game.getPlayer1());
            playerTurn(game);

            // verify if the game has ended
            if (game.getGameEnd() == 1) {
                return;
            }

            game.setActivePlayer(game.getPlayer2());
            playerTurn(game);
            if (game.getGameEnd() == 1) {
                return;
            }
        } else {
            // if starting player is second player
            game.setActivePlayer(game.getPlayer2());
            playerTurn(game);
            if (game.getGameEnd() == 1) {
                return;
            }
            game.setActivePlayer(game.getPlayer1());
            playerTurn(game);
            if (game.getGameEnd() == 1) {
                return;
            }
        }
        game.setRoundCount(game.getRoundCount() + 1);
    }

    /**
     * prepares the beginning of a round
     * gives each player mana and a card from the deck
     * @param game
     */
    void startRound(final GameInfo game) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        // adds mana
        player1.setMana(player1.getMana() + game.getRoundCount());

        // add card in hand
        if (!player1.getDeck().isEmpty()) {
            player1.getHandCards().add(player1.getDeck().get(0));

            // verify f it is environment
            if (player1.getDeck().get(0).verifyEnvCard() == 1) {
                player1.getHandEnv().add(player1.getDeck().get(0));
            }
            player1.getDeck().remove(0);
        }

        // same for second player
        player2.setMana(player2.getMana() + game.getRoundCount());
        if (!player2.getDeck().isEmpty()) {
            player2.getHandCards().add(player2.getDeck().get(0));

            if (player2.getDeck().get(0).verifyEnvCard() == 1) {
                player2.getHandEnv().add(player2.getDeck().get(0));
            }
            player2.getDeck().remove(0);
        }

        executeRound(game);
    }

    /**
     * executes the commands received during the player's turn
     * @param game
     */
    void playerTurn(final GameInfo game) {

        ArrayList<ActionsInput> actions = game.getGameInput().getActions();

        while (game.getCurrentAction() < actions.size()) {

            // if the player's turn is over
            if (actions.get(game.getCurrentAction()).getCommand().equals("endPlayerTurn")) {

                // verify if game ended
                if (game.getGameEnd() == 1) {
                    return;
                }

                // unfreezes cards and sets them as unused
                if (game.getActivePlayer() == game.getPlayer1()) {
                    game.getPlayer1().setHeroAttacked(0);
                    for (int i = 0; i < Constants.MAX_CARDS; i++) {
                        game.getGameTable().setFrozenElem(Constants.R2, i, 0);
                        game.getGameTable().setFrozenElem(Constants.R3, i, 0);
                        game.getGameTable().setHasAttackedElem(Constants.R2, i, 0);
                        game.getGameTable().setHasAttackedElem(Constants.R3, i, 0);
                    }
                } else {
                    game.getPlayer2().setHeroAttacked(0);
                    for (int i = 0; i < Constants.MAX_CARDS; i++) {
                        game.getGameTable().setFrozenElem(Constants.R0, i, 0);
                        game.getGameTable().setFrozenElem(Constants.R1, i, 0);
                        game.getGameTable().setHasAttackedElem(Constants.R0, i, 0);
                        game.getGameTable().setHasAttackedElem(Constants.R1, i, 0);
                    }
                }
                game.setCurrentAction(game.getCurrentAction() + 1);
                return;
            }
            // executes the command
            executeCommand(game, actions.get(game.getCurrentAction()));
            game.setCurrentAction(game.getCurrentAction() + 1);
        }
    }


    /**
     * prepares and starts the game
     * @param game
     */
    public void playGame(final GameInfo game) {
        game.setGameEnd(0);
        game.setCurrentAction(0);

        while (game.getCurrentAction() < game.getGameInput().getActions().size()) {
            startRound(game);
            // verify if game ended
            if (game.getGameEnd() == 1) {
                return;
            }
        }
    }
}
