package commands;

import cards.Card;
import cards.heroCards.HeroCard;
import cards.minionCards.*;
import fileio.ActionsInput;
import components.Constants;
import components.GameInfo;
import components.Player;

import java.util.ArrayList;

public class TableCommands {


    /**
     *
     * @param game
     * @param action
     */
    public void placeCard(final GameInfo game, final ActionsInput action) {
        // gaseste cartea
        Card card;
        Player player = game.getActivePlayer();

        if (action.getHandIdx() >= player.getHandCards().size()) {
            return;
        }

        card = player.getHandCards().get(action.getHandIdx());

        // verifica daca e env
        if (card.verifyEnvCard() == 1) {
            game.getOutput().addObject().put("command", "placeCard")
                    .put("handIdx", action.getHandIdx())
                    .put("error", "Cannot place environment card on table.");
            return;
        }

        //verifica daca e destula mana
        if (card.getMana() > player.getMana()) {
            game.getOutput().addObject().put("command", "placeCard")
                    .put("handIdx", action.getHandIdx())
                    .put("error", "Not enough mana to place card on table.");
            return;
        }

        // verifica pe ce rand ar tb plasata
        ArrayList<MinionCard> row;
        ArrayList<Boolean> frozen;
        if (card.verifyBackRow() == 1 && game.getActivePlayer() == game.getPlayer1()) {
            row = game.getGameTable().getRows().get(Constants.R3);
        } else if (card.verifyBackRow() == 1 && game.getActivePlayer() == game.getPlayer2()) {
            row = game.getGameTable().getRows().get(Constants.R0);
        } else if (card.verifyBackRow() == 0 && game.getActivePlayer() == game.getPlayer1()) {
            row = game.getGameTable().getRows().get(Constants.R2);
        } else {
            row = game.getGameTable().getRows().get(Constants.R1);
        }

        //verifica daca are loc
        if (row.size() >= Constants.MAX_CARDS) {
            game.getOutput().addObject().put("command", "placeCard")
                    .put("handIdx", action.getHandIdx())
                    .put("error", "Cannot place card on table since row is full.");
            return;
        }

        // adauga cartea pe masa
        if (card.getName().equals("The Ripper")) {
            row.add(new TheRipper((MinionCard) card));
        } else if (card.getName().equals("Miraj")) {
            row.add(new Miraj((MinionCard) card));
        } else if (card.getName().equals("The Cursed One")) {
            row.add(new TheCursedOne((MinionCard) card));
        } else if (card.getName().equals("Disciple")) {
            row.add(new Disciple((MinionCard) card));
        } else if (card.getName().equals("Berserker")) {
            row.add(new Berserker((MinionCard) card));
        } else if (card.getName().equals("Warden")) {
            row.add(new Warden((MinionCard) card));
        } else if (card.getName().equals("Goliath")) {
            row.add(new Goliath((MinionCard) card));
        } else if (card.getName().equals("Sentinel")) {
            row.add(new Sentinel((MinionCard) card));
        }

        // sterge cartea din mana
        player.getHandCards().remove(action.getHandIdx());

        player.setMana(player.getMana() - card.getMana());
    }


    /**
     *
     * @param game
     * @param action
     */
    public void useEnvironmentCard(final GameInfo game, final ActionsInput action) {
        Player player = game.getActivePlayer();

        Card card = player.getHandCards().get(action.getHandIdx());
        if  (card.verifyEnvCard() == 0) {
            game.getOutput().addObject().put("command", "useEnvironmentCard")
                    .put("handIdx", action.getHandIdx())
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Chosen card is not of type environment.");
            return;
        }

        // TO DO - mota aici cardEffect din envCards - partea de verificare a conditiilor
        // ca sa semene cu celelalte functii de aici

        int ret = player.getHandCards().get(action.getHandIdx()).cardEffect(game, action);

        // daca actiunea a fost facuta, cartea e starsa din mana + se scade mana
        if (ret == 0) {
            player.setMana(player.getMana() - card.getMana());
            player.getHandCards().remove(card);
            player.getHandEnv().remove(card);
        }
    }


    /**
     *
     * @param row
     * @return
     */
    public int existsTank(final ArrayList<MinionCard> row) {
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).getName().equals("Goliath") || row.get(i).getName().equals("Warden")) {
                return 1;
            }
        }
        return 0;
    }


    /**
     *
     * @param game
     * @param action
     */
    public void cardUsesAttack(final GameInfo game, final ActionsInput action) {

        // se verifica daca exista carte la pozitia atacata
        int ofX = action.getCardAttacker().getX();
        int ofY = action.getCardAttacker().getY();
        int defX = action.getCardAttacked().getX();
        int defY = action.getCardAttacked().getY();

        if (defY >= game.getGameTable().getRows().get(defX).size()) {
            return;
        }

        // se verifica daca cartea atacata e cartea inamicului
        if (game.getActivePlayer() == game.getPlayer1()) {
            if (defX == Constants.R2 || defX == Constants.R3) {
                game.getOutput().addObject().put("command", "cardUsesAttack")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .putPOJO("cardAttacked", action.getCardAttacked())
                        .put("error", "Attacked card does not belong to the enemy.");
                return;
            }
        } else {
            if (defX == Constants.R1 || defX == Constants.R0) {
                game.getOutput().addObject().put("command", "cardUsesAttack")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .putPOJO("cardAttacked", action.getCardAttacked())
                        .put("error", "Attacked card does not belong to the enemy.");
                return;
            }
        }

        // se verifica daca cartea a atacat runda asta
        if (game.getGameTable().getHasAttacked()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAttack")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card has already attacked this turn.");
            return;
        }

        // se verifica daca cartea atacatorului e inghetata
        if (game.getGameTable().getFrozen()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAttack")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card is frozen.");
            return;
        }

        MinionCard attackedCard = game.getGameTable().getRows().get(defX).get(defY);
        MinionCard attackerCard = game.getGameTable().getRows().get(ofX).get(ofY);

        // verifica daca inamicul are carti tank si daca da, verifica daca cartea atacata e tank
        if (game.getActivePlayer() == game.getPlayer1()) {
            if (existsTank(game.getGameTable().getRows().get(1)) == 1) {
                if (!(attackedCard.getName().equals("Warden")
                        || attackedCard.getName().equals("Goliath"))) {
                    game.getOutput().addObject().put("command", "cardUsesAttack")
                            .putPOJO("cardAttacker", action.getCardAttacker())
                            .putPOJO("cardAttacked", action.getCardAttacked())
                            .put("error", "Attacked card is not of type 'Tank’.");
                    return;
                }
            }
        } else if (game.getActivePlayer() == game.getPlayer2()) {
            if (existsTank(game.getGameTable().getRows().get(2)) == 1) {
                if (!(attackedCard.getName().equals("Warden")
                        || attackedCard.getName().equals("Goliath"))) {
                    game.getOutput().addObject().put("command", "cardUsesAttack")
                            .putPOJO("cardAttacker", action.getCardAttacker())
                            .putPOJO("cardAttacked", action.getCardAttacked())
                            .put("error", "Attacked card is not of type 'Tank'.");
                    return;
                }
            }
        }

        // executa atacul
        attackedCard.setHealth(attackedCard.getHealth() - attackerCard.getAttackDamage());
        if (attackedCard.getHealth() <= 0) {
            game.getGameTable().getRows().get(defX).remove(attackedCard);
        }

        // seteaza cartea ca folosita pt runda curenta
        game.getGameTable().getHasAttacked()[ofX][ofY] = 1;
    }


    /**
     *
     * @param game
     * @param action
     */
    public void cardUsesAbility(final GameInfo game, final ActionsInput action) {

        int ofX = action.getCardAttacker().getX();
        int ofY = action.getCardAttacker().getY();
        int defX = action.getCardAttacked().getX();
        int defY = action.getCardAttacked().getY();


        // se verifica daca exista carte la pozitia atacata
        if (defY >= game.getGameTable().getRows().get(defX).size()) {
            return;
        }



        // se verifica daca cartea atacatorului e inghetata
        if (game.getGameTable().getFrozen()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAbility")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card is frozen.");
            return;
        }


        // se verifica daca cartea a atacat runda asta
        if (game.getGameTable().getHasAttacked()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAbility")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card has already attacked this turn.");
            return;
        }



        // se verifica daca cartea e Disciple + daca cartea atacata e a jucatorului curent
        MinionCard ofCard = game.getGameTable().getRows().get(ofX).get(ofY);
        MinionCard defCard = game.getGameTable().getRows().get(defX).get(defY);


        if (ofCard.getName().equals("Disciple")) {
            if (game.getActivePlayer() == game.getPlayer1() && (defX == 0 || defX == 1)) {
                game.getOutput().addObject().put("command", "cardUsesAbility")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .putPOJO("cardAttacked", action.getCardAttacked())
                        .put("error", "Attacked card does not belong to the current player.");
                return;
            } else if (game.getActivePlayer() == game.getPlayer2()
                    && (defX == Constants.R2 || defX == Constants.R3)) {
                game.getOutput().addObject().put("command", "cardUsesAbility")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .putPOJO("cardAttacked", action.getCardAttacked())
                        .put("error", "Attacked card does not belong to the current player.");
                return;
            }

            // se verifica daca cartea e The Ripper, Miraj sau The Cursed One
        } else if (ofCard.getName().equals("The Ripper") || ofCard.getName().equals("Miraj")
                || ofCard.getName().equals("The Cursed One")) {


            // daca cartea atacata e a jucatorului curent => eroare
            if (game.getActivePlayer() == game.getPlayer1()
                    && (defX == Constants.R2 || defX == Constants.R3)) {
                game.getOutput().addObject().put("command", "cardUsesAbility")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .putPOJO("cardAttacked", action.getCardAttacked())
                        .put("error", "Attacked card does not belong to the enemy.");
                return;
            } else if (game.getActivePlayer() == game.getPlayer2() && (defX == 0 || defX == 1)) {
                game.getOutput().addObject().put("command", "cardUsesAbility")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .putPOJO("cardAttacked", action.getCardAttacked())
                        .put("error", "Attacked card does not belong to the enemy.");
                return;
            }


            // se verifica existenta un carti Tank
            if (game.getActivePlayer() == game.getPlayer1()
                    && existsTank(game.getGameTable().getRows().get(1)) == 1) {
                if (!(defCard.getName().equals("Warden") || defCard.getName().equals("Goliath"))) {
                    game.getOutput().addObject().put("command", "cardUsesAbility")
                            .putPOJO("cardAttacker", action.getCardAttacker())
                            .putPOJO("cardAttacked", action.getCardAttacked())
                            .put("error", "Attacked card is not of type 'Tank'.");
                    return;
                }
            } else if (game.getActivePlayer() == game.getPlayer2()
                    && existsTank(game.getGameTable().getRows().get(Constants.R2)) == 1) {
                    if (!(defCard.getName().equals("Warden")
                            || defCard.getName().equals("Goliath"))) {
                        game.getOutput().addObject().put("command", "cardUsesAbility")
                                .putPOJO("cardAttacker", action.getCardAttacker())
                                .putPOJO("cardAttacked", action.getCardAttacked())
                                .put("error", "Attacked card is not of type 'Tank'.");
                        return;
                    }
            }

            // daca e orice alt tip de minion, nu are abilitate speciala, deci return
        } else {
            return;
        }

        // se foloseste abilitatea
        ofCard.cardEffect(game, action);


        // seteaza cartea ca folosita pt runda curenta
        game.getGameTable().getHasAttacked()[ofX][ofY] = 1;
    }


    /**
     *
     * @param game
     * @param action
     */
    public void useAttackHero(final GameInfo game, final ActionsInput action) {

        int ofX = action.getCardAttacker().getX();
        int ofY = action.getCardAttacker().getY();

        // se verifica daca exista carte la care sa atace
        if (ofY >= game.getGameTable().getRows().get(ofX).size()) {
            return;
        }

        MinionCard ofCard = game.getGameTable().getRows().get(ofX).get(ofY);


        // se verifica daca cartea atacatorului e inghetata
        if (game.getGameTable().getFrozen()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "useAttackHero")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .put("error", "Attacker card is frozen.");
            return;
        }

        // se verifica daca cartea a atacat runda asta
        if (game.getGameTable().getHasAttacked()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "useAttackHero")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .put("error", "Attacker card has already attacked this turn.");
            return;
        }


        // se verifica daca exista carti Tank
        if (game.getActivePlayer() == game.getPlayer1()
                && existsTank(game.getGameTable().getRows().get(1)) == 1) {
                game.getOutput().addObject().put("command", "useAttackHero")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .put("error", "Attacked card is not of type 'Tank'.");
                return;
        } else if (game.getActivePlayer() == game.getPlayer2()
                && existsTank(game.getGameTable().getRows().get(2)) == 1) {
                game.getOutput().addObject().put("command", "useAttackHero")
                        .putPOJO("cardAttacker", action.getCardAttacker())
                        .put("error", "Attacked card is not of type 'Tank'.");
                return;
        }

        HeroCard defHero;

        // atacul propriu-zis
        if (game.getActivePlayer() == game.getPlayer1()) {
            defHero = game.getPlayer2().getHero();
        } else {
            defHero = game.getPlayer1().getHero();
        }

        defHero.setHealth(defHero.getHealth() - ofCard.getAttackDamage());
        game.getGameTable().getHasAttacked()[ofX][ofY] = 1;

        if (defHero.getHealth() <= 0) {
            game.setGameEnd(1);
            game.getStats().setgPlayed(game.getStats().getgPlayed() + 1);
            if (game.getActivePlayer() == game.getPlayer1()) {
                game.getOutput().addObject().put("gameEnded", "Player one killed the enemy hero.");
                game.getStats().setpOneWins(game.getStats().getpOneWins() + 1);
            } else {
                game.getOutput().addObject().put("gameEnded", "Player two killed the enemy hero.");
                game.getStats().setpTwoWins(game.getStats().getpTwoWins() + 1);
            }
        }

    }


    /**
     *
     * @param game
     * @param action
     */
    public void useHeroAbility(final GameInfo game, final ActionsInput action) {

        HeroCard hero;
        Player player = game.getActivePlayer();

        if (game.getActivePlayer() == game.getPlayer1()) {
            hero = game.getPlayer1().getHero();
        } else {
            hero = game.getPlayer2().getHero();
        }


        //verifica daca e destula mana
        if (hero.getMana() > player.getMana()) {
            game.getOutput().addObject().put("command", "useHeroAbility")
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Not enough mana to use hero's ability.");
            return;
        }

        // se verifica daca eroul a atacat runda asta
        if (player.getHeroAttacked() == 1) {
            game.getOutput().addObject().put("command", "useHeroAbility")
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Hero has already attacked this turn.");
            return;
        }



        // verifica daca randul apartine jucatorului care tb
        if (hero.getName().equals("Lord Royce") || hero.getName().equals("Empress Thorina")) {
            if (game.getActivePlayer() == game.getPlayer1()
                    && (action.getAffectedRow() == Constants.R2
                    || action.getAffectedRow() == Constants.R3)) {
                game.getOutput().addObject().put("command", "useHeroAbility")
                        .put("affectedRow", action.getAffectedRow())
                        .put("error", "Selected row does not belong to the enemy.");
                return;
            } else if (game.getActivePlayer() == game.getPlayer2()
                    && (action.getAffectedRow() == 0 || action.getAffectedRow() == 1)) {
                game.getOutput().addObject().put("command", "useHeroAbility")
                        .put("affectedRow", action.getAffectedRow())
                        .put("error", "Selected row does not belong to the enemy.");
                return;
            }
        } else if (hero.getName().equals("General Kocioraw")
                || hero.getName().equals("King Mudface")) {
            if (game.getActivePlayer() == game.getPlayer1()
                    && (action.getAffectedRow() == 0 || action.getAffectedRow() == 1)) {
                game.getOutput().addObject().put("command", "useHeroAbility")
                        .put("affectedRow", action.getAffectedRow())
                        .put("error", "Selected row does not belong to the current player.");
                return;
            } else if (game.getActivePlayer() == game.getPlayer2()
                    && (action.getAffectedRow() == Constants.R2
                    || action.getAffectedRow() == Constants.R3)) {
                game.getOutput().addObject().put("command", "useHeroAbility")
                        .put("affectedRow", action.getAffectedRow())
                        .put("error", "Selected row does not belong to the current player.");
                return;
            }
        }


        // executa actiunea
        hero.cardEffect(game, action);
        player.setMana(player.getMana() - hero.getMana());
        player.setHeroAttacked(1);
    }
}
