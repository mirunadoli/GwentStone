package cards.heroCards;

import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

import java.util.ArrayList;

public class GeneralKocioraw extends HeroCard {

    public GeneralKocioraw() {
    }

    public GeneralKocioraw(final CardInput card) {
        super(card);
    }

    public GeneralKocioraw(final HeroCard card) {
        super(card);
    }

    /**
     * +1 attack damage for all card on row
     * @param game
     * @param action
     * @return
     */
    @Override
    public int cardEffect(final GameInfo game, final ActionsInput action) {

        ArrayList<MinionCard> row = game.getGameTable().getRows().get(action.getAffectedRow());

        for (int i = 0; i < row.size(); i++) {
            row.get(i).setAttackDamage(row.get(i).getAttackDamage() + 1);
        }

        return 0;
    }
}
