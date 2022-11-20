package cards.minionCards;

import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

public class TheCursedOne extends MinionCard {

    public TheCursedOne() {
    }

    public TheCursedOne(final CardInput card) {
        super(card);
    }

    public TheCursedOne(final MinionCard card) {
        super(card);
    }


    /**
     * swap between health and attack damage
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        MinionCard card = game.getGameTable().getRows().get(action.getCardAttacked().getX())
                .get(action.getCardAttacked().getY());

        int health = card.getHealth();
        card.setHealth(card.getAttackDamage());
        card.setAttackDamage(health);

        // if new health is zero, destroy the card
        if (card.getHealth() <= 0) {
            game.getGameTable().getRows().get(action.getCardAttacked().getX()).remove(card);
        }

        return 0;
    }
}
