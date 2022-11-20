package cards.heroCards;

import cards.minionCards.MinionCard;
import fileio.ActionsInput;
import fileio.CardInput;
import components.GameInfo;

import java.util.ArrayList;

public class EmpressThorina extends HeroCard {
    public EmpressThorina() {
    }

    public EmpressThorina(final CardInput card) {
        super(card);
    }

    public EmpressThorina(final HeroCard card) {
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
        // distruge cartea cu cea mai mare viata de pe rand
        ArrayList<MinionCard> row = game.getGameTable().getRows().get(action.getAffectedRow());

        // daca nu exista carti pe randul ales
        if (row.size() == 0) {
            return 0;
        }

        // gasirea cartii cu viata cea mai mare
        MinionCard targetCard = row.get(0);
        int x = action.getAffectedRow();
        int y = 0;

        for (int i = 1; i < row.size(); i++) {
            if (targetCard.getHealth() <= row.get(i).getHealth()) {
                targetCard = row.get(i);
                y = i;
            }
        }

        // distrugerea cartii
        game.getGameTable().getRows().get(x).remove(y);
        return 0;
    }
}
