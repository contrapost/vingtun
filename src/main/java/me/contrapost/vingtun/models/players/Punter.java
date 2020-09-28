package me.contrapost.vingtun.models.players;

import me.contrapost.vingtun.models.cards.Card;

import java.util.ArrayList;

import static me.contrapost.vingtun.game.util.VingtUnUtil.getPlayersScore;

public class Punter implements Player {

    private final Dealer dealer;
    private final String name;
    private final ArrayList<Card> receivedCards = new ArrayList<>();

    public Punter(final Dealer dealer, final String name) {
        this.dealer = dealer;
        this.name = name;
    }

    @Override
    public void receiveCards(final int numberOfCards) {
        receivedCards.addAll(dealer.dealCards(numberOfCards));
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
    public boolean canDrawCard(final int limit) {
        return getPlayersScore(this) < limit;
    }
}
