package me.contrapost.vingtun;

import java.util.Objects;

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

    @Override
    public String toString() {
        return suit.getValue() + cardValue.getDesignation();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Card card = (Card) o;
        return suit == card.suit &&
               cardValue == card.cardValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, cardValue);
    }
}
