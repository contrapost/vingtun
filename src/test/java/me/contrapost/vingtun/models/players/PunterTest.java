package me.contrapost.vingtun.models.players;

import me.contrapost.vingtun.models.cards.Card;
import me.contrapost.vingtun.models.cards.CardValue;
import me.contrapost.vingtun.models.cards.Deck;
import me.contrapost.vingtun.models.cards.Suit;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static me.contrapost.vingtun.FileTestUtil.createFile;
import static org.junit.Assert.*;

public class PunterTest {

    @Test
    public void playerReceivedCorrectCards() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK\n";
        String fileName = createFile(content);
        Deck deckFromFile = new Deck(fileName);

        Dealer dealer = new Dealer(deckFromFile);
        Punter punter = new Punter(dealer, "test");
        punter.receiveCards(2);
        ArrayList<Card> cards = punter.getReceivedCards();
        assertEquals(cards.get(0), new Card(Suit.DIAMOND, CardValue.KING));
        assertEquals(cards.get(1), new Card(Suit.CLUB, CardValue.SEVEN));
    }

    @Test
    public void canDrawCardIfLimitIsNotReached() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK\n";
        String fileName = createFile(content);
        Deck deckFromFile = new Deck(fileName);

        Dealer dealer = new Dealer(deckFromFile);
        Punter punter = new Punter(dealer, "test");
        punter.receiveCards(2); // punter got DK and C7 (17)
        assertFalse(punter.canDrawCard(17));
        assertTrue(punter.canDrawCard(18));
    }
}
