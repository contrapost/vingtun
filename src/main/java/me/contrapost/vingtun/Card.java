package me.contrapost.vingtun;

public class Card {

    private final Suit suit;
    private final CardValue cardValue;

    public Card(final Suit suit, final CardValue cardValue) {
        this.suit = suit;
        this.cardValue = cardValue;
    }

    public Suit getSuit() {
        return suit;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    public String toString() {
        return suit.getValue() + cardValue.getDesignation();
    }
}
