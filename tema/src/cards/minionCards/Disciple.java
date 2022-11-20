package cards.minionCards;

import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

public class Disciple extends MinionCard {


    public Disciple() {
    }

    public Disciple(final CardInput card) {
        super(card);
    }

    public Disciple(final MinionCard card) {
        super(card);
    }

    /**
     * +2 health for a minion
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        MinionCard card = game.getGameTable().getRows().get(action.getCardAttacked().getX())
                .get(action.getCardAttacked().getY());

        card.setHealth(card.getHealth() + 2);
        return 0;
    }
}
