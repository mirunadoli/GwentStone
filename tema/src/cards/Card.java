package cards;

import fileio.ActionsInput;
import fileio.CardInput;
import main.GameInfo;

import java.util.ArrayList;

 public class Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Card() {
    }

    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public Card(final Card card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

     public final String getDescription() {
        return description;
    }

     public final void setDescription(final String description) {
        this.description = description;
    }

     public final ArrayList<String> getColors() {
        return colors;
    }

     public final void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

     public final String getName() {
        return name;
    }

     public final void setName(final String name) {
        this.name = name;
    }

     /**
      *
      * @return
      */
     public final int verifyEnvCard() {
        if (name.equals("Firestorm")
                || name.equals("Winterfell")
                || name.equals("Heart Hound")) {
            return 1;
        }
        return 0;
    }

     /**
      *
      * @return
      */
     public final int verifyBackRow() {
        if (name.equals("Sentinel")
                || name.equals("Berserker")
                || name.equals("The Cursed One")
                || name.equals("Disciple")) {
            return 1;
        }
        return 0;
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
