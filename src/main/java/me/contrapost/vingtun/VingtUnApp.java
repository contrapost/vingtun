package me.contrapost.vingtun;

import java.util.ArrayList;

import static me.contrapost.vingtun.CLIProcessor.getFileNameFromCLI;

public class VingtUnApp {

    public static void main(final String[] args) {
        String deckFileName = getFileNameFromCLI(args);
        ArrayList<Card> deck = new Deck(deckFileName).getCards();
        deck.forEach(System.out::println);
    }
}
