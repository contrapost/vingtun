package me.contrapost.vingtun;

import java.util.NoSuchElementException;

public enum Suit {
    CLUB("C"),
    DIAMOND("D"),
    HEART("H"),
    SPADE("S");

    private final String value;

    Suit(final String value) {
        this.value = value;
    }

    public static Suit getByValue(final String value) {
        for (Suit suit : values()) {
            if (suit.value.equals(value)) {
                return suit;
            }
        }
        throw new NoSuchElementException(
                "me.contrapost.vingtun.Suit with value '" + value + "' doesn't exist.");
    }

    public String getValue() {
        return value;
    }
}
