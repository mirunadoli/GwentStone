package cards.envCards;

import cards.Card;
import cards.minionCards.MinionCard;
import components.Constants;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

import java.util.ArrayList;

public class HeartHound extends EnvironmentCard {

    public HeartHound() {
    }

    public HeartHound(final CardInput card) {
        super(card);
    }

    public HeartHound(final Card card) {
        super(card);
    }


    /**
     * apply card effect
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        if (super.cardEffect(game, action) != 0) {
            return -1;
        }

        ArrayList<MinionCard> row = game.getGameTable().getRows().get(action.getAffectedRow());
        ArrayList<MinionCard> mirRow;
        MinionCard card;

        // find mirrored row
        if (action.getAffectedRow() == Constants.R0) {
            mirRow = game.getGameTable().getRows().get(Constants.R3);
        } else if (action.getAffectedRow() == Constants.R1) {
            mirRow = game.getGameTable().getRows().get(Constants.R2);
        } else if (action.getAffectedRow() == Constants.R2) {
            mirRow = game.getGameTable().getRows().get(Constants.R1);
        } else {
            mirRow = game.getGameTable().getRows().get(Constants.R0);
        }

        // verify if mirrored row is full
        if (mirRow.size() >= Constants.MAX_CARDS) {
            game.getOutput().addObject().put("command", "useEnvironmentCard")
                    .put("handIdx", action.getHandIdx())
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Cannot steal enemy card since the player's row is full.");
            return -1;
        }

        // verify if row is empty
        if (row.isEmpty()) {
            return -1;
        }

        // search for the card with most health
        card = row.get(0);
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).getHealth() >= card.getHealth()) {
                card = row.get(i);
            }
        }

        // adds it to the mirrored row
        mirRow.add(card);
        row.remove(card);
        return 0;
    }
}
