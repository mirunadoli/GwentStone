package cards.minionCards;

import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

public class TheRipper extends MinionCard {

    public TheRipper() {
    }

    public TheRipper(final CardInput card) {
        super(card);
    }

    public TheRipper(final MinionCard card) {
        super(card);
    }

    /**
     *
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        // scade atacul cartii selectate cu 2
        MinionCard card = game.getGameTable().getRows().get(action.getCardAttacked().getX())
                .get(action.getCardAttacked().getY());

        card.setAttackDamage(card.getAttackDamage() - 2);
        if (card.getAttackDamage() < 0) {
            card.setAttackDamage(0);
        }
        return 0;
    }
}
