package cards.envCards;

import cards.Card;
import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

import java.util.ArrayList;

public class Firestorm extends EnvironmentCard {

    public Firestorm() {
    }

    public Firestorm(final CardInput card) {
        super(card);
    }

    public Firestorm(final Card card) {
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
        for (int i = 0; i < row.size(); i++) {
            row.get(i).setHealth(row.get(i).getHealth() - 1);
        }

        int size = row.size();
        for (int i = size - 1; i >= 0; i--) {
            if (row.get(i).getHealth() <= 0) {
                row.remove(i);
            }
        }

        return 0;
    }

}
