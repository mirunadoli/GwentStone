package cards.envCards;

import cards.Card;
import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

import java.util.ArrayList;

public class Winterfell extends EnvironmentCard {

    public Winterfell() {
    }

    public Winterfell(final CardInput card) {
        super(card);
    }

    public Winterfell(final Card card) {
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

        for (int i = 0; i < row.size(); i++) {
            game.getGameTable().getFrozen()[action.getAffectedRow()][i] = 1;
        }

        return 0;
    }
}
