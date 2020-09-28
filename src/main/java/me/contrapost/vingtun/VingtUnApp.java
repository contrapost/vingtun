package me.contrapost.vingtun;

import java.util.ArrayList;

import static me.contrapost.vingtun.CLIProcessor.getFileNameFromCLI;

public class VingtUnApp {

    public static void main(final String[] args) {
        String deckFileName = getFileNameFromCLI(args);
        Deck deck = new Deck(deckFileName);
        if (deck.isNew()) {
            System.out.println("New deck of cards was initialized.");
        }
        ArrayList<Card> cards = deck.getCards();
        Card lastCard = cards.remove(cards.size() - 1);
        System.out.println(lastCard);
        System.out.println();
        deck.getCards().forEach(System.out::println);
    }
}
