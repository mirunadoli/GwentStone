package cards.heroCards;

import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

import java.util.ArrayList;

public class EmpressThorina extends HeroCard {
    public EmpressThorina() {
    }

    public EmpressThorina(final CardInput card) {
        super(card);
    }

    public EmpressThorina(final HeroCard card) {
        super(card);
    }

    /**
     * destroys card with most health from the selected row
     * @param game
     * @param action
     * @return
     */
    @Override
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        ArrayList<MinionCard> row = game.getGameTable().getRows().get(action.getAffectedRow());

        // if ther are no cards
        if (row.size() == 0) {
            return 0;
        }

        // find the card with most life
        MinionCard targetCard = row.get(0);
        int x = action.getAffectedRow();
        int y = 0;

        for (int i = 1; i < row.size(); i++) {
            if (targetCard.getHealth() <= row.get(i).getHealth()) {
                targetCard = row.get(i);
                y = i;
            }
        }

        // destroy the card
        game.getGameTable().getRows().get(x).remove(y);
        return 0;
    }
}
