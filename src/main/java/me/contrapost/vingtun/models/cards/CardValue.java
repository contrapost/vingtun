package me.contrapost.vingtun.models.cards;

import javax.annotation.Nonnull;
import java.util.NoSuchElementException;

/**
 * Possible card values with designations
 */
public enum CardValue {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(10, "J"),
    QUEEN(10, "Q"),
    KING(10, "K"),
    ACE(11, "A");

    private final int value;
    private final String designation;

    CardValue(final int value, final String designation) {
        this.value = value;
        this.designation = designation;
    }

    public static CardValue getByDesignation(@Nonnull final String designation) {
        for (CardValue cardValue : values()) {
            if (cardValue.designation.equals(designation)) {
                return cardValue;
            }
        }
        throw new NoSuchElementException(
                CardValue.class.getSimpleName() + " with designation '" + designation + "' doesn't exist.");
    }

    public int getValue() {
        return value;
    }

    public String getDesignation() {
        return designation;
    }
}
