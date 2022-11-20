package cards.heroCards;

import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

import java.util.ArrayList;

public class LordRoyce extends HeroCard {

    public LordRoyce() {
    }

    public LordRoyce(final CardInput card) {
        super(card);
    }

    public LordRoyce(final HeroCard card) {
        super(card);
    }

    /**
     * freezes card with most attack damage
     * @param game
     * @param action
     * @return
     */
    @Override
    public int cardEffect(final GameInfo game, final ActionsInput action) {

        ArrayList<MinionCard> row = game.getGameTable().getRows().get(action.getAffectedRow());

        // if there are no cards
        if (row.size() == 0) {
            return 0;
        }

        // find card with most attack damage
        MinionCard targetCard = row.get(0);
        int x = action.getAffectedRow();
        int y = 0;

        for (int i = 1; i < row.size(); i++) {
            if (targetCard.getAttackDamage() < row.get(i).getAttackDamage()) {
                targetCard = row.get(i);
                y = i;
            }
        }

        // sets card as frozen
        game.getGameTable().getFrozen()[x][y] = 1;
        return 0;
    }
}
