package me.contrapost.vingtun.util;

import me.contrapost.vingtun.models.cards.Card;
import me.contrapost.vingtun.models.cards.CardValue;
import me.contrapost.vingtun.models.cards.Suit;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.contrapost.vingtun.game.util.VingtUnConstants.NUMBER_OF_CARDS;

public class DeckProcessor {

    public final static String CARD_REGEX = "([HDCS]([2-9]|10|[JKQA]))";

    private DeckProcessor() {
    }

    public static ArrayList<Card> getFromFile(final String deckFileName) {
        ArrayList<Card> deck;
        Path deckFilePath = Paths.get(deckFileName);
        if (!Files.exists(deckFilePath)) {
            System.err.println("File with deck '" + deckFileName + "' doesn't exist.");
            deck = null;
        } else {
            try {
                String sanitizedContent = Files.readString(deckFilePath, StandardCharsets.US_ASCII)
                                               .trim()
                                               .replaceAll(", ", "");
                if (!fileContentIsValid(sanitizedContent)) {
                    System.err.println(
                            "Content of the file '" + deckFileName + "' cannot be converted to deck of cards.");
                    deck = null;
                } else {
                    ArrayList<Card> cards = convertContentToCards(sanitizedContent);
                    if (!correctSetOfCards(cards)) {
                        System.err.println("The set of cards from the file '" + deckFileName + "' is incorrect.");
                        deck = null;
                    } else {
                        deck = cards;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error while reading file with deck.");
                deck = null;
            }
        }
        return deck;
    }

    public static ArrayList<Card> initiateNewDeck() {
        ArrayList<Card> cards = new ArrayList<>(NUMBER_OF_CARDS);
        Arrays.stream(CardValue.values())
              .forEach(cardValue -> Arrays.stream(Suit.values())
                                          .map(suit -> new Card(suit, cardValue))
                                          .forEach(cards::add));
        return cards;
    }

    private static boolean fileContentIsValid(final String content) {
        return content.matches("^" + CARD_REGEX + "{" + NUMBER_OF_CARDS + "}$");
    }

    private static ArrayList<Card> convertContentToCards(final String content) {
        ArrayList<String> allMatches = new ArrayList<>(NUMBER_OF_CARDS);
        Matcher matcher = Pattern.compile(CARD_REGEX).matcher(content);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        return allMatches.stream()
                         .map(cardCode -> new Card(Suit.getByValue(cardCode.substring(0, 1)),
                                                   CardValue.getByDesignation(cardCode.substring(1))))
                         .collect(Collectors.toCollection(ArrayList::new));
    }

    private static boolean correctSetOfCards(final ArrayList<Card> cards) {
        return new HashSet<>(cards).size() == 52;
    }
}
