package me.contrapost.vingtun;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeckProcessorTest {

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
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK";
        String fileName = createFile(content);
        ArrayList<Card> deck = DeckProcessor.getFromFile(fileName);
        String fromDeck = deck.stream().map(Card::toString).collect(Collectors.joining(", "));
        assertEquals(content, fromDeck);
    }

    @Test
    public void fileWithDuplicatedCardsResultsInReturningNull() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, DK, DK"; // 2 DK cards
        String fileName = createFile(content);
        ArrayList<Card> deck = DeckProcessor.getFromFile(fileName);
        assertNull(deck);
    }

    @Test
    public void fileWithInvalidContentResultsInReturningNull() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, invalid, DK";
        String fileName = createFile(content);
        ArrayList<Card> deck = DeckProcessor.getFromFile(fileName);
        assertNull(deck);
    }

    @Test
    public void initiateNewDeckCreatesDeckWith52UniqueCards() {
        ArrayList<Card> cards = DeckProcessor.initiateNewDeck();
        assertEquals(cards.size(), 52);
        assertEquals(new HashSet<>(cards).size(), 52);
    }

    private String createFile(final String content) throws IOException {
        Path tempFile = Files.createTempFile("testDeckFile", ".tmp");
        tempFile.toFile().deleteOnExit();

        Files.write(tempFile, content.getBytes());

        return tempFile.toString();
    }
}
