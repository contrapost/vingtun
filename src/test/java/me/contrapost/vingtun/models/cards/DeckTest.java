package me.contrapost.vingtun.models.cards;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static me.contrapost.vingtun.FileTestUtil.createFile;
import static me.contrapost.vingtun.game.util.VingtUnConstants.NUMBER_OF_CARDS;
import static me.contrapost.vingtun.util.DeckProcessor.CARD_REGEX;
import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void deckInitiatedFromFileAssumedAsShuffledAndIsEqualToFileContent() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK\n";
        String fileName = createFile(content);
        Deck deckFromFile = new Deck(fileName);

        ArrayList<Card> cards = deckToCards(deckFromFile);
        Collections.reverse(cards);
        String fromDeck = cards.stream().map(Card::toString).collect(Collectors.joining(", "));

        assertTrue(deckFromFile.isShuffled());
        assertEquals(content.trim(), fromDeck);
    }

    @Test
    public void newCreatedDeckContainsCorrectSetOfCardsAndIsNotShuffled(){
        Deck newDeck = new Deck(null);
        ArrayList<Card> cards = deckToCards(newDeck);

        assertFalse(newDeck.isShuffled());
        assertEquals(new HashSet<>(cards).size(), NUMBER_OF_CARDS); // 52 unique cards
        cards.forEach(card -> assertTrue(card.toString().matches(CARD_REGEX)));
    }

    @Test
    public void orderOfCardsIsChangedAfterDeckIsShuffled() {
        Deck newDeck = new Deck(null);
        String deckBeforeShufflingAsString = newDeck.toString();
        ArrayList<String> cardsBeforeShuffling = cardsFromString(deckBeforeShufflingAsString);
        newDeck.shuffle();
        String deckAfterShufflingAsString = newDeck.toString();
        ArrayList<String> cardsAfterShuffling = cardsFromString(deckAfterShufflingAsString);

        assertNotEquals(cardsBeforeShuffling, cardsAfterShuffling);
        assertEquals(new HashSet<>(cardsBeforeShuffling), new HashSet<>(cardsAfterShuffling));
    }

    @Test
    public void getCardFromTopReturnCorrectCard() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK\n";
        String fileName = createFile(content);
        Deck deckFromFile = new Deck(fileName);

        Card firstFromTop = deckFromFile.getCardFromTop();
        Card secondFromTop = deckFromFile.getCardFromTop();
        String deckAfterDealingTwoCards = deckFromFile.toString();
        ArrayList<String> cardsAfterDealingTwoCards = cardsFromString(deckAfterDealingTwoCards);
        assertEquals(cardsAfterDealingTwoCards.size(), 50);
        assertEquals(firstFromTop, new Card(Suit.DIAMOND, CardValue.KING));
        assertEquals(secondFromTop, new Card(Suit.CLUB, CardValue.SEVEN));
    }

    private ArrayList<String> cardsFromString(String cardsAsString) {
        ArrayList<String> allMatches = new ArrayList<>(NUMBER_OF_CARDS);
        Matcher matcher = Pattern.compile(CARD_REGEX).matcher(cardsAsString);
        while (matcher.find()) {
            allMatches.add(matcher.group());
        }
        return new ArrayList<>(allMatches);
    }

    private ArrayList<Card> deckToCards(Deck deck) {
        ArrayList<Card> cards = new ArrayList<>(NUMBER_OF_CARDS);
        IntStream.range(0, NUMBER_OF_CARDS).forEach(i -> cards.add(deck.getCardFromTop()));
        return cards;
    }
}
