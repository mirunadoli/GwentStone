package cards.minionCards;

import fileio.ActionsInput;
import fileio.CardInput;
import main.GameInfo;

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
     *
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        // +2 la viata unui minion din tabara lui
        MinionCard card = game.getGameTable().getRows().get(action.getCardAttacked().getX())
                .get(action.getCardAttacked().getY());

        card.setHealth(card.getHealth() + 2);
        return 0;
    }
}
