package me.contrapost.vingtun;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Deck {

    private final Logger logger = Logger.getLogger("Logger");

    private final ArrayList<Card> cards;

    public Deck(final String deckFileName) {
        if (null == deckFileName) {
            cards = getShuffledDeck();
        } else {
            cards = getFromFile(deckFileName);
        }
    }

    private ArrayList<Card> getShuffledDeck() {
        ArrayList<Card> cards = new ArrayList<>(52);
        Arrays.stream(CardValue.values())
              .forEach(cardValue -> Arrays.stream(Suit.values())
                                     .map(suit -> new Card(suit, cardValue))
                                     .forEach(cards::add));
        IntStream.range(1, 10).parallel().forEach(i -> Collections.shuffle(cards));
        return cards;
    }



    private ArrayList<Card> getFromFile(final String deckFileName) {
        ArrayList<Card> deck = new ArrayList<>();
        Path deckFilePath = Paths.get(deckFileName);
        if (Files.exists(deckFilePath)) {
            try {
                String content = Files.readString(deckFilePath, StandardCharsets.US_ASCII);
                System.out.println(content);
            } catch (IOException e) {
                System.err.println("Error while reading file with deck. Initializing and shuffling a new deck.");
                deck = getShuffledDeck();
            }
        } else {
            System.out.println("File with deck '" + deckFileName + "' doesn't exist. Initializing and shuffling a new deck.");
            deck = getShuffledDeck();
        }
        return deck;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
