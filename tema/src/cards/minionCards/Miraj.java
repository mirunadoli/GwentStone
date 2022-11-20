package cards.minionCards;

import fileio.ActionsInput;
import fileio.CardInput;
import main.GameInfo;

public class Miraj extends MinionCard {

    public Miraj() {
    }

    public Miraj(final CardInput card) {
        super(card);
    }

    public Miraj(final MinionCard card) {
        super(card);
    }

    /**
     *
     * @param game
     * @param action
     * @return
     */
    public int cardEffect(final GameInfo game, final ActionsInput action) {
        //face swap intre viata lui si viata celeilalte carti
        MinionCard defCard = game.getGameTable().getRows().get(action.getCardAttacked().getX())
                .get(action.getCardAttacked().getY());
        MinionCard ofCard = game.getGameTable().getRows().get(action.getCardAttacker().getX())
                .get(action.getCardAttacker().getY());

        int health = ofCard.getHealth();
        ofCard.setHealth(defCard.getHealth());
        defCard.setHealth(health);
        return 0;
    }
}
