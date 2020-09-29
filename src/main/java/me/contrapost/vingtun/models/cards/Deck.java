package me.contrapost.vingtun.models.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

import static me.contrapost.vingtun.util.DeckProcessor.getFromFile;
import static me.contrapost.vingtun.util.DeckProcessor.initiateNewDeck;

public class Deck {

    private final ArrayList<Card> cards;
    private boolean shuffled;

    public Deck(final String deckFileName) {
        if (null == deckFileName) {
            cards = initiateNewDeck();
            shuffled = false;
        } else {
            ArrayList<Card> cardsFromFile = getFromFile(deckFileName);
            cards = cardsFromFile == null ? initiateNewDeck() : cardsFromFile;
            shuffled = true;
        }
    }

    public void shuffle() {
        shuffle(10);
    }

    public void shuffle(final int times) {
        IntStream.range(0, times).forEach(i -> Collections.shuffle(cards));
        shuffled = true;
    }

    public Card getCardFromTop() {
        return cards.remove(cards.size() - 1);
    }

    public boolean isShuffled() {
        return shuffled;
    }

    @Override
    public String toString() {
        return "Deck{" +
               "cards=" + cards +
               ", isShuffled=" + shuffled +
               '}';
    }
}
