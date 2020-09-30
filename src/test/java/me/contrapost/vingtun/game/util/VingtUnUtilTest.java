package me.contrapost.vingtun.game.util;

import me.contrapost.vingtun.TestSet;
import me.contrapost.vingtun.models.cards.Card;
import me.contrapost.vingtun.models.cards.CardValue;
import me.contrapost.vingtun.models.cards.Suit;
import me.contrapost.vingtun.models.game.GameResult;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static me.contrapost.vingtun.game.util.VingtUnConstants.PUNTER_ADDITIONAL_CARD_LIMIT;
import static me.contrapost.vingtun.game.util.VingtUnUtil.*;
import static org.junit.Assert.*;

public class VingtUnUtilTest {

    @Test
    public void getPlayersScoreCalculatesCorrectValue() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, DQ, C8\n";

        TestSet testSet = new TestSet(content);
        testSet.getPunter().receiveCards(2); // C8 + DQ = 8 + 10 = 18
        int score = getPlayersScore(testSet.getPunter());
        assertEquals(score, 18);
    }

    @Test
    public void dealOriginalCardsResultsInPlayersHaveCardsInCorrectOrder() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DA, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, SA, H9, S2, CA, C10, CQ, DQ, C8\n";

        TestSet testSet = new TestSet(content);
        dealOriginalCards(testSet.getPunter(), testSet.getDealer()); // punter: [C8, CQ], dealer: [DQ, C10]

        assertEquals(testSet.getDealer().getReceivedCards().size(), 2);
        assertTrue(testSet.getDealer().getReceivedCards().contains(new Card(Suit.DIAMOND, CardValue.QUEEN)));
        assertTrue(testSet.getDealer().getReceivedCards().contains(new Card(Suit.CLUB, CardValue.TEN)));

        assertEquals(testSet.getPunter().getReceivedCards().size(), 2);
        assertTrue(testSet.getPunter().getReceivedCards().contains(new Card(Suit.CLUB, CardValue.EIGHT)));
        assertTrue(testSet.getPunter().getReceivedCards().contains(new Card(Suit.CLUB, CardValue.QUEEN)));
    }

    @Test
    public void getOriginalCardDealResult_bothPunterAndDealerHaveBlackJack_punterWins() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, CA, C10, CQ, DA, SA\n";

        TestSet testSet = new TestSet(content);
        dealOriginalCards(testSet.getPunter(), testSet.getDealer()); // punter: [SA, CQ], dealer: [DA, C10]
        Optional<GameResult>  gameResult = getOriginalCardDealResult(testSet.getPunter(), testSet.getDealer());
        assertTrue(gameResult.isPresent());
        assertEquals(gameResult.get().getWinner(), testSet.getPunter());
        assertEquals(gameResult.get().getLoser(), testSet.getDealer());
    }

    @Test
    public void getOriginalCardDealResult_onlyPunterHasBlackJack_punterWins() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C10, S9, DK, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, CA, C2, CQ, DA, SA\n";

        TestSet testSet = new TestSet(content);
        dealOriginalCards(testSet.getPunter(), testSet.getDealer()); // punter: [SA, CQ], dealer: [DA, C2]
        Optional<GameResult>  gameResult = getOriginalCardDealResult(testSet.getPunter(), testSet.getDealer());
        assertTrue(gameResult.isPresent());
        assertEquals(gameResult.get().getWinner(), testSet.getPunter());
        assertEquals(gameResult.get().getLoser(), testSet.getDealer());
    }

    @Test
    public void getOriginalCardDealResult_OnlyDealerHasBlackJack_dealerWins() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, SA, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, CA, C10, CQ, DA, H8\n";

        TestSet testSet = new TestSet(content);
        dealOriginalCards(testSet.getPunter(), testSet.getDealer()); // punter: [H8, CQ], dealer: [DA, C10]
        Optional<GameResult>  gameResult = getOriginalCardDealResult(testSet.getPunter(), testSet.getDealer());
        assertTrue(gameResult.isPresent());
        assertEquals(gameResult.get().getWinner(), testSet.getDealer());
        assertEquals(gameResult.get().getLoser(), testSet.getPunter());
    }

    @Test
    public void getOriginalCardDealResult_bothPunterAndDealerHave22_dealerWins() throws IOException {
        String content = "S3, H6, SJ, CQ, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, C10, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, H8, SA, HA, DA, CA\n";

        TestSet testSet = new TestSet(content);
        dealOriginalCards(testSet.getPunter(), testSet.getDealer()); // punter: [CA, HA], dealer: [DA, SA]
        Optional<GameResult> gameResult = getOriginalCardDealResult(testSet.getPunter(), testSet.getDealer());
        assertTrue(gameResult.isPresent());
        assertEquals(gameResult.get().getWinner(), testSet.getDealer());
        assertEquals(gameResult.get().getLoser(), testSet.getPunter());
    }

    @Test
    public void getOriginalCardDealResult_resultCannotBeDecided_resultIsNull() throws IOException {
        String content = "CA, H6, SJ, CQ, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, DA, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, C10, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, H8, SA, HA, C4, S3\n";

        TestSet testSet = new TestSet(content);
        dealOriginalCards(testSet.getPunter(), testSet.getDealer()); // punter: [S3, HA], dealer: [C4, SA]
        Optional<GameResult> gameResult = getOriginalCardDealResult(testSet.getPunter(), testSet.getDealer());
        assertTrue(gameResult.isEmpty());
    }

    @Test
    public void dealAdditionalCards_playerHasLessScoreThanLimit_shouldReceiveCardsWithScoreMoreThanLimit()
            throws IOException {
        String content = "CA, H6, SJ, CQ, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, DA, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, C10, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, H8, SA, HA, C4, S3\n";

        TestSet testSet = new TestSet(content);
        testSet.getPunter().receiveCards(2); // S3 + C4 = 7
        int originalCardsValue = getPlayersScore(testSet.getPunter());
        dealAddictionCards(testSet.getPunter(), PUNTER_ADDITIONAL_CARD_LIMIT); // HA = 11
        int originalAndAdditionalCardsValue = getPlayersScore(testSet.getPunter()); // S3 + C4 + HA = 18

        assertEquals(originalCardsValue, 7);
        assertEquals(originalAndAdditionalCardsValue, 18);
        assertTrue(originalCardsValue < PUNTER_ADDITIONAL_CARD_LIMIT);
        assertTrue(originalAndAdditionalCardsValue > PUNTER_ADDITIONAL_CARD_LIMIT);
    }

    @Test
    public void getAdditionalCardResult_playerHasMoreThan21_playerLost() throws IOException {
        String content = "CA, H6, SJ, CQ, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, DA, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, C10, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, H8, SA, HA, C4, S3\n";

        TestSet testSet = new TestSet(content);
        testSet.getPunter().receiveCards(4); // S3, C4, HA, SA
        Optional<GameResult> result = getResultWithAdditionalCards(testSet.getPunter(), testSet.getDealer());

        assertTrue(result.isPresent());
        assertEquals(result.get().getWinner(), testSet.getDealer());
        assertEquals(result.get().getLoser(), testSet.getPunter());
    }

    @Test
    public void getAdditionalCardResult_playerHasLessThan21_resultIsNull() throws IOException {
        String content = "CA, H6, SJ, CQ, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, DA, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, C10, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, H8, SA, HA, C4, S3\n";

        TestSet testSet = new TestSet(content);
        testSet.getPunter().receiveCards(3); // S3, C4, HA
        Optional<GameResult> result = getResultWithAdditionalCards(testSet.getPunter(), testSet.getDealer());

        assertTrue(result.isEmpty());
    }
}
