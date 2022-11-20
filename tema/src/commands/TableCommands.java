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
     * places a card on the table
     * @param game
     * @param action
     */
    public void placeCard(final GameInfo game, final ActionsInput action) {
        // find the card
        Card card;
        Player player = game.getActivePlayer();

        // if the card doesn't exist - return
        if (action.getHandIdx() >= player.getHandCards().size()) {
            return;
        }

        card = player.getHandCards().get(action.getHandIdx());

        // verify if it is environment
        if (card.verifyEnvCard() == 1) {
            game.getOutput().addObject().put("command", "placeCard")
                    .put("handIdx", action.getHandIdx())
                    .put("error", "Cannot place environment card on table.");
            return;
        }

        // verify if player has enough mana
        if (card.getMana() > player.getMana()) {
            game.getOutput().addObject().put("command", "placeCard")
                    .put("handIdx", action.getHandIdx())
                    .put("error", "Not enough mana to place card on table.");
            return;
        }

        // verify on which row it should be placed
        ArrayList<MinionCard> row;
        if (card.verifyBackRow() == 1 && game.getActivePlayer() == game.getPlayer1()) {
            row = game.getGameTable().getRows().get(Constants.R3);
        } else if (card.verifyBackRow() == 1 && game.getActivePlayer() == game.getPlayer2()) {
            row = game.getGameTable().getRows().get(Constants.R0);
        } else if (card.verifyBackRow() == 0 && game.getActivePlayer() == game.getPlayer1()) {
            row = game.getGameTable().getRows().get(Constants.R2);
        } else {
            row = game.getGameTable().getRows().get(Constants.R1);
        }

        // verify if there is space on the row
        if (row.size() >= Constants.MAX_CARDS) {
            game.getOutput().addObject().put("command", "placeCard")
                    .put("handIdx", action.getHandIdx())
                    .put("error", "Cannot place card on table since row is full.");
            return;
        }

        // adds the card on table
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

        // deletes the card from hand and decreases mana
        player.getHandCards().remove(action.getHandIdx());
        player.setMana(player.getMana() - card.getMana());
    }


    /**
     * uses the ability of a environment card
     * @param game
     * @param action
     */
    public void useEnvironmentCard(final GameInfo game, final ActionsInput action) {
        Player player = game.getActivePlayer();
        Card card = player.getHandCards().get(action.getHandIdx());

        // verify if card is environment
        if  (card.verifyEnvCard() == 0) {
            game.getOutput().addObject().put("command", "useEnvironmentCard")
                    .put("handIdx", action.getHandIdx())
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Chosen card is not of type environment.");
            return;
        }

        int ret = player.getHandCards().get(action.getHandIdx()).cardEffect(game, action);

        // if the effect was applied, removes the card from hand
        if (ret == 0) {
            player.setMana(player.getMana() - card.getMana());
            player.getHandCards().remove(card);
            player.getHandEnv().remove(card);
        }
    }


    /**
     * verify if there exists a tank card on the row
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
     * implements the attack of a card
     * @param game
     * @param action
     */
    public void cardUsesAttack(final GameInfo game, final ActionsInput action) {

        int ofX = action.getCardAttacker().getX();
        int ofY = action.getCardAttacker().getY();
        int defX = action.getCardAttacked().getX();
        int defY = action.getCardAttacked().getY();

        // verify if the card attacked exists
        if (defY >= game.getGameTable().getRows().get(defX).size()) {
            return;
        }

        // verify if the card attacked belongs to the enemy
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

        // verify if the attacker card has attacked this round already
        if (game.getGameTable().getHasAttacked()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAttack")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card has already attacked this turn.");
            return;
        }

        // verify if the attacker card is frozen
        if (game.getGameTable().getFrozen()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAttack")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card is frozen.");
            return;
        }

        MinionCard attackedCard = game.getGameTable().getRows().get(defX).get(defY);
        MinionCard attackerCard = game.getGameTable().getRows().get(ofX).get(ofY);

        // verify if the enemy has tank cards and if the attacked card is tank
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

        // executes the attack
        attackedCard.setHealth(attackedCard.getHealth() - attackerCard.getAttackDamage());
        if (attackedCard.getHealth() <= 0) {
            game.getGameTable().getRows().get(defX).remove(attackedCard);
        }

        // sets attacker card as used this round
        game.getGameTable().getHasAttacked()[ofX][ofY] = 1;
    }


    /**
     * uses the special ability of a card
     * @param game
     * @param action
     */
    public void cardUsesAbility(final GameInfo game, final ActionsInput action) {

        int ofX = action.getCardAttacker().getX();
        int ofY = action.getCardAttacker().getY();
        int defX = action.getCardAttacked().getX();
        int defY = action.getCardAttacked().getY();

        // verify if the card attacked exists
        if (defY >= game.getGameTable().getRows().get(defX).size()) {
            return;
        }

        // verify if the attacker card is frozen
        if (game.getGameTable().getFrozen()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAbility")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card is frozen.");
            return;
        }

        // verify if the attacker card has attacked this round already
        if (game.getGameTable().getHasAttacked()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "cardUsesAbility")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .putPOJO("cardAttacked", action.getCardAttacked())
                    .put("error", "Attacker card has already attacked this turn.");
            return;
        }

        // verify if attacker card is Disciple and if attacked card belongs to current player
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

            // verify if attacker card is The Ripper, Miraj or The Cursed One
        } else if (ofCard.getName().equals("The Ripper") || ofCard.getName().equals("Miraj")
                || ofCard.getName().equals("The Cursed One")) {


            // verify if attacked card belongs to the enemy
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

            // verify if a tank card is on the table
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

            // if the card is any other type of minion, it doesn't have a special ability
        } else {
            return;
        }

        // uses the ability
        ofCard.cardEffect(game, action);
        game.getGameTable().getHasAttacked()[ofX][ofY] = 1;
    }


    /**
     * uses an attack on the hero
     * @param game
     * @param action
     */
    public void useAttackHero(final GameInfo game, final ActionsInput action) {

        int ofX = action.getCardAttacker().getX();
        int ofY = action.getCardAttacker().getY();

        // verify if the attacker card exists
        if (ofY >= game.getGameTable().getRows().get(ofX).size()) {
            return;
        }

        MinionCard ofCard = game.getGameTable().getRows().get(ofX).get(ofY);

        // verify if the attacker card is frozen
        if (game.getGameTable().getFrozen()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "useAttackHero")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .put("error", "Attacker card is frozen.");
            return;
        }

        // verify if the attacker card has attacked this round already
        if (game.getGameTable().getHasAttacked()[ofX][ofY] == 1) {
            game.getOutput().addObject().put("command", "useAttackHero")
                    .putPOJO("cardAttacker", action.getCardAttacker())
                    .put("error", "Attacker card has already attacked this turn.");
            return;
        }


        // verify if a tank card is on the table
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

        // executes the attac
        if (game.getActivePlayer() == game.getPlayer1()) {
            defHero = game.getPlayer2().getHero();
        } else {
            defHero = game.getPlayer1().getHero();
        }

        defHero.setHealth(defHero.getHealth() - ofCard.getAttackDamage());
        game.getGameTable().getHasAttacked()[ofX][ofY] = 1;

        // if the hero health is under 0, end game
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
     * uses a hero's ability
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

        // verify if the player has enough mana
        if (hero.getMana() > player.getMana()) {
            game.getOutput().addObject().put("command", "useHeroAbility")
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Not enough mana to use hero's ability.");
            return;
        }

        // verify if the hero has already attacked this round
        if (player.getHeroAttacked() == 1) {
            game.getOutput().addObject().put("command", "useHeroAbility")
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Hero has already attacked this turn.");
            return;
        }

        // verify if attacked row belongs to the right player
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

        // executes the attack
        hero.cardEffect(game, action);
        player.setMana(player.getMana() - hero.getMana());
        player.setHeroAttacked(1);
    }
}
