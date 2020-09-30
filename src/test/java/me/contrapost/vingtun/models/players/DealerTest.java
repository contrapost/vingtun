package me.contrapost.vingtun.models.players;

import me.contrapost.vingtun.TestSet;
import me.contrapost.vingtun.models.cards.Card;
import me.contrapost.vingtun.models.cards.CardValue;
import me.contrapost.vingtun.models.cards.Suit;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static me.contrapost.vingtun.game.util.VingtUnUtil.getPlayersScore;
import static org.junit.Assert.*;

public class DealerTest {

    @Test
    public void canDrawCardIfPunterHasGreaterScore() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK\n";
        TestSet testSet = new TestSet(content);

        testSet.getPunter().receiveCards(2); // punter got DK and C7 (17)
        int punterScore = getPlayersScore(testSet.getPunter());
        assertTrue(testSet.getDealer().canDrawCard(punterScore));
        testSet.getDealer().receiveCards(2); // dealer got CQ and C10 (21)
        assertFalse(testSet.getDealer().canDrawCard(punterScore));
    }

    @Test
    public void dealCardsInCorrectOrder() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, DQ, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, C8, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, C7, DK\n";
        TestSet testSet = new TestSet(content);

        ArrayList<Card> cards = testSet.getDealer().dealCards(3);
        assertEquals(cards.get(0), new Card(Suit.DIAMOND, CardValue.KING));
        assertEquals(cards.get(1), new Card(Suit.CLUB, CardValue.SEVEN));
        assertEquals(cards.get(2), new Card(Suit.CLUB, CardValue.QUEEN));
    }

}
