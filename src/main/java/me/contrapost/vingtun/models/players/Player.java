package me.contrapost.vingtun.models.players;

import me.contrapost.vingtun.models.cards.Card;

import java.util.List;

/**
 * Interface with common "features" of a player (i.e. dealer or punter)
 */
public interface Player {

    void receiveCards(int numberOfCards);

    List<Card> getReceivedCards();

    String getName();

    boolean canDrawCard(final int limit);
}
