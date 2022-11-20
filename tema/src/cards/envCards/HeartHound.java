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
     *
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

        // gasirea randului oglindit
        if (action.getAffectedRow() == Constants.R0) {
            mirRow = game.getGameTable().getRows().get(Constants.R3);
        } else if (action.getAffectedRow() == Constants.R1) {
            mirRow = game.getGameTable().getRows().get(Constants.R2);
        } else if (action.getAffectedRow() == Constants.R2) {
            mirRow = game.getGameTable().getRows().get(Constants.R1);
        } else {
            mirRow = game.getGameTable().getRows().get(Constants.R0);
        }
        // verifica conditia speciala pt HeartHound
        if (mirRow.size() >= Constants.MAX_CARDS) {
            game.getOutput().addObject().put("command", "useEnvironmentCard")
                    .put("handIdx", action.getHandIdx())
                    .put("affectedRow", action.getAffectedRow())
                    .put("error", "Cannot steal enemy card since the player's row is full.");
            return -1;
        }

        //verifica daca exista carti pe randul selectat
        if (row.isEmpty()) {
            return -1;
        }

        // cautarea cartii cu viata cea mai mare
        card = row.get(0);
        for (int i = 0; i < row.size(); i++) {
            if (row.get(i).getHealth() >= card.getHealth()) {
                card = row.get(i);
            }
        }

        // adaugarea cartii pe randul oglindit
        mirRow.add(card);
        row.remove(card);
        return 0;
    }
}
