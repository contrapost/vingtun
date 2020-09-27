package me.contrapost.vingtun;

import java.util.ArrayList;

import static me.contrapost.vingtun.DeckProcessor.getFromFile;
import static me.contrapost.vingtun.DeckProcessor.initiateNewDeck;

public class Deck {

    private final ArrayList<Card> cards;
    private final boolean isNew;

    public Deck(final String deckFileName) {
        if (null == deckFileName) {
            cards = initiateNewDeck();
            isNew = true;
        } else {
            ArrayList<Card> cardsFromFile = getFromFile(deckFileName);
            cards = cardsFromFile == null ? initiateNewDeck() : cardsFromFile;
            isNew = false;
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean isNew() {
        return isNew;
    }
}
