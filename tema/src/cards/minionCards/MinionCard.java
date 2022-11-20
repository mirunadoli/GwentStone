package cards.minionCards;

import cards.Card;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

public class MinionCard extends Card {
    private int attackDamage;
    private int health;

    public MinionCard() {
    }

    public MinionCard(final CardInput card) {
        super(card);
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
    }

    public MinionCard(final MinionCard card) {
        super(card);
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
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
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        return 0;
    }

}
