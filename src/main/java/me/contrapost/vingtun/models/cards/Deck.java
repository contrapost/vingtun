package me.contrapost.vingtun.models.cards;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

import static me.contrapost.vingtun.util.DeckProcessor.getFromFile;
import static me.contrapost.vingtun.util.DeckProcessor.initiateNewDeck;

/**
 * Class representing deck of cards
 */
public class Deck {

    private final ArrayList<Card> cards;
    private boolean shuffled;

    public Deck(@Nullable final String deckFileName) {
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

    /**
     * Return one card from the top of the deck (last card in cards array list)
     * @return {@link Card}
     * @throws EmptyDeckException   when the deck is empty. Shouldn't be reached with existing game rules, can be useful
     *                              when/if game is extended for multiple players
     */
    public Card getCardFromTop() {
        if (cards.isEmpty()) {
            throw new EmptyDeckException();
        } else {
            return cards.remove(cards.size() - 1);
        }
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
