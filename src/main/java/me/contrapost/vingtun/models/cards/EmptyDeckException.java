package me.contrapost.vingtun.models.cards;

public class EmptyDeckException extends RuntimeException {

    public EmptyDeckException() {
        super();
    }

    public EmptyDeckException(final String message) {
        super(message);
    }
}
