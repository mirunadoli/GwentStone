package cards.heroCards;

import cards.Card;
import components.Constants;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

public class HeroCard extends Card {
    private int health;

    public HeroCard() {
    }

    public HeroCard(final CardInput card) {
        super(card);
        this.health = Constants.START_HEALTH;
    }

    public HeroCard(final HeroCard card) {
        super(card);
        this.health = card.getHealth();
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @param game
     * @param action
     * @return
     */
    @Override
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        return 0;
    }
}
