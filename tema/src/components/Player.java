package components;

import cards.Card;
import cards.heroCards.HeroCard;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> deck;
    private ArrayList<Card> handCards;
    private ArrayList<Card> handEnv;
    private HeroCard hero;
    private int heroAttacked;
    private int mana;

    public Player() {
        this.deck = new ArrayList<>();
        this.handCards = new ArrayList<>();
        this.handEnv = new ArrayList<>();
        this.hero = new HeroCard();
        this.heroAttacked = 0;
        this.mana = 0;

    }



    public final ArrayList<Card> getDeck() {
        return deck;
    }

    public final void setDeck(final ArrayList<Card> deck) {
        this.deck = deck;
    }

    public final ArrayList<Card> getHandCards() {
        return handCards;
    }

    public final void setHandCards(final ArrayList<Card> handCards) {
        this.handCards = handCards;
    }

    public final ArrayList<Card> getHandEnv() {
        return handEnv;
    }

    public final void setHandEnv(final ArrayList<Card> handEnv) {
        this.handEnv = handEnv;
    }

    public final HeroCard getHero() {
        return hero;
    }

    public final void setHero(final HeroCard hero) {
        this.hero = hero;
    }

    public final int getMana() {
        return mana;
    }

    public final void setMana(final int mana) {
        this.mana = mana;
    }

    public final int getHeroAttacked() {
        return heroAttacked;
    }

    public final void setHeroAttacked(final int heroAttacked) {
        this.heroAttacked = heroAttacked;
    }
}
