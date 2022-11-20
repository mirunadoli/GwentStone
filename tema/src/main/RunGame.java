package main;

import fileio.ActionsInput;
import main.commands.Debugger;
import main.commands.TableCommands;

import java.util.ArrayList;

public class RunGame {
    //static int roundCount;
    private final Debugger debug = new Debugger();
    private final TableCommands table = new TableCommands();

    /**
     *
     * @param game
     * @param action
     */
    void executeCommand(final GameInfo game, final ActionsInput action) {
        // to do - if mare care trimite actiunile acolo unde tb executate
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
     *
     * @param game
     */
    void executeRound(final GameInfo game) {
        if (game.getGameInput().getStartGame().getStartingPlayer() == 1) {
            game.setActivePlayer(game.getPlayer1());
            playerTurn(game);
            // se verifica daca jocul s-a terminat
            if (game.getGameEnd() == 1) {
                return;
            }

            game.setActivePlayer(game.getPlayer2());
            playerTurn(game);
            // se verifica daca jocul s-a terminat
            if (game.getGameEnd() == 1) {
                return;
            }
        } else {
            game.setActivePlayer(game.getPlayer2());
            playerTurn(game);
            // se verifica daca jocul s-a terminat
            if (game.getGameEnd() == 1) {
                return;
            }
            game.setActivePlayer(game.getPlayer1());
            playerTurn(game);
            // se verifica daca jocul s-a terminat
            if (game.getGameEnd() == 1) {
                return;
            }
        }
        game.setRoundCount(game.getRoundCount() + 1);
    }

    /**
     *
     * @param game
     */
    void startRound(final GameInfo game) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();


        // adauga mana
        player1.setMana(player1.getMana() + game.getRoundCount());

        // adauga carte in mana
        if (!player1.getDeck().isEmpty()) {
            player1.getHandCards().add(player1.getDeck().get(0));

            // daca e de tip environment o adauga in handEnv
            if (player1.getDeck().get(0).verifyEnvCard() == 1) {
                player1.getHandEnv().add(player1.getDeck().get(0));
            }
            player1.getDeck().remove(0);
        }


        // pt jucatorul 2
        player2.setMana(player2.getMana() + game.getRoundCount());
        if (!player2.getDeck().isEmpty()) {
            player2.getHandCards().add(player2.getDeck().get(0));

            // daca e de tip environment o adauga in handEnv
            if (player2.getDeck().get(0).verifyEnvCard() == 1) {
                player2.getHandEnv().add(player2.getDeck().get(0));
            }
            player2.getDeck().remove(0);
        }

        executeRound(game);
    }

    /**
     *
     * @param game
     */
    void playerTurn(final GameInfo game) {

        ArrayList<ActionsInput> actions = game.getGameInput().getActions();

        while (game.getCurrentAction() < actions.size()) {
            if (actions.get(game.getCurrentAction()).getCommand().equals("endPlayerTurn")) {

                // se verifica daca jocul s-a terminat
                if (game.getGameEnd() == 1) {
                    return;
                }


                // dezgheata cartile playerului curent + rezolva hasAttacked
                if (game.getActivePlayer() == game.getPlayer1()) {
                    game.getPlayer1().setHeroAttacked(0);
                    for (int i = 0; i < 5; i++) {
                        game.getGameTable().setFrozenElem(2, i, 0);
                        game.getGameTable().setFrozenElem(3, i, 0);
                        game.getGameTable().setHasAttackedElem(2, i, 0);
                        game.getGameTable().setHasAttackedElem(3, i, 0);
                    }
                } else {
                    game.getPlayer2().setHeroAttacked(0);
                    for (int i = 0; i < 5; i++) {
                        game.getGameTable().setFrozenElem(0, i, 0);
                        game.getGameTable().setFrozenElem(1, i, 0);
                        game.getGameTable().setHasAttackedElem(0, i, 0);
                        game.getGameTable().setHasAttackedElem(1, i, 0);
                    }
                }

                game.setCurrentAction(game.getCurrentAction() + 1);
                return;
            }
            executeCommand(game, actions.get(game.getCurrentAction()));
            game.setCurrentAction(game.getCurrentAction() + 1);
        }
    }


    /**
     *
     * @param game
     */
    void playGame(final GameInfo game) {
        game.setGameEnd(0);
        //roundCount = 1;
        game.setCurrentAction(0);

        while (game.getCurrentAction() < game.getGameInput().getActions().size()) {
            startRound(game);
            // se verifica daca jocul s-a terminat
            if (game.getGameEnd() == 1) {
                return;
            }
        }
    }
}
