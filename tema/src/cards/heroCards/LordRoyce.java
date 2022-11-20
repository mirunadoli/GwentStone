package cards.heroCards;

import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import fileio.CardInput;
import main.GameInfo;

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
     *
     * @param game
     * @param action
     * @return
     */
    @Override
    public int cardEffect(final GameInfo game, final ActionsInput action) {

        // ingheata cartea cu cel mai mare atac de pe rand
        ArrayList<MinionCard> row = game.getGameTable().getRows().get(action.getAffectedRow());

        // daca nu exista carti pe randul ales
        if (row.size() == 0) {
            return 0;
        }

        // gasirea cartii cu atacul cel mai mare
        MinionCard targetCard = row.get(0);
        int x = action.getAffectedRow();
        int y = 0;

        for (int i = 1; i < row.size(); i++) {
            if (targetCard.getAttackDamage() < row.get(i).getAttackDamage()) {
                targetCard = row.get(i);
                y = i;
            }
        }

        // setarea cartii ca frozen
        game.getGameTable().getFrozen()[x][y] = 1;
        return 0;
    }
}
