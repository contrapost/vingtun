package me.contrapost.vingtun.models.players;

import me.contrapost.vingtun.models.cards.Card;
import me.contrapost.vingtun.models.cards.Deck;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static me.contrapost.vingtun.game.util.VingtUnUtil.getPlayersScore;

/**
 * Class that represents a dealer
 */
public class Dealer implements Player {

    private final Deck deck;
    private final String name;
    private final ArrayList<Card> receivedCards = new ArrayList<>();

    public Dealer(final Deck deck) {
        this.deck = deck;
        this.name = "dealer";
    }

    public Dealer(final Deck deck, final String dealerName) {
        this.deck = deck;
        name = dealerName;
    }

    @Override
    public void receiveCards(final int numberOfCards) {
        receivedCards.addAll(dealCards(numberOfCards));
    }

    @Override
    public ArrayList<Card> getReceivedCards() {
        return receivedCards;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean canDrawCard(final int punterFinalScore) {
        return getPlayersScore(this) <= punterFinalScore;
    }

    public ArrayList<Card> dealCards(final int numberOfCards) {
        ArrayList<Card> cardsToDeal = new ArrayList<>(numberOfCards);
        IntStream.range(0, numberOfCards).forEach(i -> cardsToDeal.add(deck.getCardFromTop()));
        return cardsToDeal;
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public Deck getDeck() {
        return deck;
    }
}
