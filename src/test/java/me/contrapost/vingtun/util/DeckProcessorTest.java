package me.contrapost.vingtun.util;

import me.contrapost.vingtun.models.cards.Card;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import static me.contrapost.vingtun.FileTestUtil.createFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DeckProcessorTest {

    @Test
    public void nonExistingFileResultsInReturningNull() {
        String deckFileName = "not_existing_file";
        ArrayList<Card> deck = DeckProcessor.getFromFile(deckFileName);
        assertNull(deck);
    }

    @Test
    public void fileWithValidContentConvertedToListOfCards() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK\n";
        String fileName = createFile(content);
        ArrayList<Card> cards = DeckProcessor.getFromFile(fileName);
        String fromDeck = cards.stream().map(Card::toString).collect(Collectors.joining(", "));
        assertEquals(content.trim(), fromDeck);
    }

    @Test
    public void fileWithDuplicatedCardsResultsInReturningNull() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, DK, DK"; // 2 DK cards
        String fileName = createFile(content);
        ArrayList<Card> cards = DeckProcessor.getFromFile(fileName);
        assertNull(cards);
    }

    @Test
    public void fileWithInvalidContentResultsInReturningNull() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, invalid, DK";
        String fileName = createFile(content);
        ArrayList<Card> cards = DeckProcessor.getFromFile(fileName);
        assertNull(cards);
    }

    @Test
    public void initiateNewDeckCreatesDeckWith52UniqueCards() {
        ArrayList<Card> cards = DeckProcessor.initiateNewDeck();
        assertEquals(cards.size(), 52);
        assertEquals(new HashSet<>(cards).size(), 52);
    }
}
