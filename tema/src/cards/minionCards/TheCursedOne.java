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
     *
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        // face swap intre viata si atacul vietii selectate
        MinionCard card = game.getGameTable().getRows().get(action.getCardAttacked().getX())
                .get(action.getCardAttacked().getY());

        int health = card.getHealth();
        card.setHealth(card.getAttackDamage());
        card.setAttackDamage(health);

        //daca noua viata e zero, cartea e distrusa
        if (card.getHealth() <= 0) {
            game.getGameTable().getRows().get(action.getCardAttacked().getX()).remove(card);
        }

        return 0;
    }
}
