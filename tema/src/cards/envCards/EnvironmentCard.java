package cards.envCards;

import cards.Card;
import components.Constants;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;
import components.Player;

public class EnvironmentCard extends Card {
    public EnvironmentCard() {
    }
    public EnvironmentCard(final CardInput card) {
        super(card);
    }

    public EnvironmentCard(final Card card) {
        super(card);
    }

    /**
     *
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        // verifica cazurile invalide
        Player player = game.getActivePlayer();

        Card card = player.getHandCards().get(action.getHandIdx());

        // daca cartea e de tip env
        if  (card.verifyEnvCard() == 0) {
            game.getOutput().addObject().put("command", "useEnvironmentCard")
                    .put("handIdx", action.getHandIdx())
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Chosen type is not of type environment.");
            return -1;
        }

        if (player.getMana() < card.getMana()) {
            game.getOutput().addObject().put("command", "useEnvironmentCard")
                    .put("handIdx", action.getHandIdx())
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Not enough mana to use environment card.");
            return -1;
        }

        if (player == game.getPlayer1()) {
            if (action.getAffectedRow() == Constants.R2
                    || action.getAffectedRow() == Constants.R3) {
                game.getOutput().addObject().put("command", "useEnvironmentCard")
                        .put("handIdx", action.getHandIdx())
                        .put("affectedRow", action.getAffectedRow())
                        .put("error", "Chosen row does not belong to the enemy.");
                return -1;
            }
        } else if (player == game.getPlayer2()) {
            if (action.getAffectedRow() == Constants.R0
                    || action.getAffectedRow() == Constants.R1) {
                game.getOutput().addObject().put("command", "useEnvironmentCard")
                        .put("handIdx", action.getHandIdx())
                        .put("affectedRow", action.getAffectedRow())
                        .put("error", "Chosen row does not belong to the enemy.");
                return -1;
            }

        }
        return 0;
    }
}
