package me.contrapost.vingtun.models.cards;

/**
 * Exception that signals case when a deck of card is empty
 * Should be thrown when new card is to be dealt
 */
public class EmptyDeckException extends RuntimeException {

    public EmptyDeckException() {
        super();
    }

    public EmptyDeckException(final String message) {
        super(message);
    }
}
