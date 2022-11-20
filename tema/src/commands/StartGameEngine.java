package commands;

import cards.Card;
import cards.heroCards.*;
import cards.minionCards.*;
import cards.envCards.Firestorm;
import cards.envCards.HeartHound;
import cards.envCards.Winterfell;
import com.fasterxml.jackson.databind.node.ArrayNode;
import components.GameInfo;
import components.GameTable;
import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;
import components.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class StartGameEngine {
    /**
     * Creates a copy of the deck that will be used in the current game
     * and gives each card the corresponding class
     * @param deck
     * @return
     */
    ArrayList<Card> copyDeck(final ArrayList<CardInput> deck) {

        // creates a new deck
        ArrayList<Card> copy = new ArrayList<>();

        // for each card in deck, verifies the type of card
        for (int i = 0; i < deck.size(); i++) {
                if (deck.get(i).getName().equals("Firestorm")) {
                    copy.add(new Firestorm(deck.get(i)));
                } else if (deck.get(i).getName().equals("Winterfell")) {
                    copy.add(new Winterfell(deck.get(i)));
                } else if (deck.get(i).getName().equals("Heart Hound")) {
                    copy.add(new HeartHound(deck.get(i)));
                } else if (deck.get(i).getName().equals("The Ripper")) {
                    copy.add(new TheRipper(deck.get(i)));
                } else if (deck.get(i).getName().equals("Warden")) {
                    copy.add(new Warden(deck.get(i)));
                } else if (deck.get(i).getName().equals("The Cursed One")) {
                    copy.add(new TheCursedOne(deck.get(i)));
                } else if (deck.get(i).getName().equals("Sentinel")) {
                    copy.add(new Sentinel(deck.get(i)));
                } else if (deck.get(i).getName().equals("Miraj")) {
                    copy.add(new Miraj(deck.get(i)));
                } else if (deck.get(i).getName().equals("Goliath")) {
                    copy.add(new Goliath(deck.get(i)));
                } else if (deck.get(i).getName().equals("Disciple")) {
                    copy.add(new Disciple(deck.get(i)));
                } else if (deck.get(i).getName().equals("Berserker")) {
                    copy.add(new Berserker(deck.get(i)));
                } else {
                    copy.add(new MinionCard(deck.get(i)));
                }
        }
        return copy;
    }

    /**
     * shuffles the deck gave as an input
     * @param deck
     * @param seed
     * @return
     */
    ArrayList<Card> shuffle(final ArrayList<CardInput> deck, final int seed) {
        ArrayList<Card> shuffledDeck = copyDeck(deck);
        Collections.shuffle(shuffledDeck, new Random(seed));
        return shuffledDeck;
    }

    /**
     *  creates a new hero based on the card type
     * @param hero
     * @return
     */
    HeroCard createNewHero(final CardInput hero) {
        HeroCard newHero;
        if (hero.getName().equals("Empress Thorina")) {
            newHero = new EmpressThorina(hero);
        } else  if (hero.getName().equals("General Kocioraw")) {
            newHero = new GeneralKocioraw(hero);
        } else if (hero.getName().equals("King Mudface")) {
            newHero = new KingMudface(hero);
        } else {
            newHero = new LordRoyce(hero);
        }
        return newHero;
    }

    /**
     * prepares the player's info for a new game start
     * @param player
     * @param shuffledDeck
     * @param hero
     */
    void createPlayer(final Player player, final ArrayList<Card> shuffledDeck,
                      final HeroCard hero) {
        player.setDeck(shuffledDeck);
        player.setHero(hero);
        player.setMana(0);
        player.setHeroAttacked(0);
        player.setHandCards(new ArrayList<>());
        player.setHandEnv(new ArrayList<>());

    }

    /**
     * creates the decks and the players for the new game
     * @param player1
     * @param decks1
     * @param player2
     * @param decks2
     * @param input
     */
    void startNewGame(final Player player1, final ArrayList<ArrayList<CardInput>> decks1,
                      final Player player2, final ArrayList<ArrayList<CardInput>> decks2,
                      final StartGameInput input) {


        int idxDeck1 = input.getPlayerOneDeckIdx();

        // create and shuffle first player's deck
        ArrayList<Card> shuffledDeck1 = shuffle(decks1.get(idxDeck1), input.getShuffleSeed());

        // first player's hero
        HeroCard heroPlayer1 = createNewHero(input.getPlayerOneHero());

        // first player
        createPlayer(player1, shuffledDeck1, heroPlayer1);

        // same for second player
        int idxDeck2 = input.getPlayerTwoDeckIdx();
        ArrayList<Card> shuffledDeck2 = shuffle(decks2.get(idxDeck2), input.getShuffleSeed());
        HeroCard heroPlayer2 = createNewHero(input.getPlayerTwoHero());
        createPlayer(player2, shuffledDeck2, heroPlayer2);

    }


    /**
     * starts the program and runs each game
     * @param inputData
     * @param output
     */
    public void startProgram(final Input inputData, final ArrayNode output) {

        ArrayList<ArrayList<CardInput>> decks1 = inputData.getPlayerOneDecks().getDecks();
        ArrayList<ArrayList<CardInput>> decks2 = inputData.getPlayerTwoDecks().getDecks();
        StartGameInput stInput;

        //necessary instants for the game
        RunGame runGame = new RunGame();
        GameTable gameTable;
        Statistics stats = new Statistics();
        Player player1;
        Player player2;

        // start each game
        for (int i = 0; i < inputData.getGames().size(); i++) {
            stInput = inputData.getGames().get(i).getStartGame();
            player1 = new Player();
            player2 = new Player();
            gameTable = new GameTable();
            startNewGame(player1, decks1, player2, decks2, stInput);
            GameInfo info = new GameInfo(player1, player2, gameTable, inputData.getGames().get(i),
                    output, stats);
            runGame.playGame(info);
        }
    }
}
