package me.contrapost.vingtun;

public enum Suit {
    CLUB("C"),
    DIAMOND("D"),
    HEART("H"),
    SPADE("S");

    private final String value;

    Suit(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
