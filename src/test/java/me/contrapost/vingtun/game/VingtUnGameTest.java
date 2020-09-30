package me.contrapost.vingtun.game;

import me.contrapost.vingtun.TestSet;
import me.contrapost.vingtun.models.game.GameResult;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class VingtUnGameTest {

    @Test
    public void playRound_newDeck_dealerShufflesDeck() throws IOException {
        TestSet testSet = new TestSet(null);
        assertFalse(testSet.getDealer().getDeck().isShuffled());
        VingtUnGame game = new VingtUnGame(testSet.getDealer(), testSet.getPunter());
        game.playRound();
        assertTrue(testSet.getDealer().getDeck().isShuffled());
    }

    @Test
    public void playRound_punterHasBlackJackWithOriginalCards_punterWins() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C10, S9, DK, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, CA, C2, CQ, DA, SA\n";

        TestSet testSet = new TestSet(content);
        VingtUnGame game = new VingtUnGame(testSet.getDealer(), testSet.getPunter());
        GameResult result = game.playRound();

        assertEquals(result.getWinner(), testSet.getPunter());
        assertEquals(result.getLoser(), testSet.getDealer());
    }

    @Test
    public void playRound_bothPunterAndDealerHave22_dealerWins() throws IOException {
        String content = "S3, H6, SJ, CQ, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, D7, C2, S9, DK, C10, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, C3, H5, SQ, C8, H9, S2, H8, SA, HA, DA, CA\n";

        TestSet testSet = new TestSet(content);
        VingtUnGame game = new VingtUnGame(testSet.getDealer(), testSet.getPunter());
        GameResult result = game.playRound();

        assertEquals(result.getLoser(), testSet.getPunter());
        assertEquals(result.getWinner(), testSet.getDealer());
    }

    @Test
    public void playRound_dealerHasMoreThan21WithAdditionalCards_punterWins() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D4, S8, H2, C5, H4, CA, C10, S9, DK, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "D8, S7, CQ, H5, S2, C8, H9, SQ, D7, C2, C3, DA, SA\n";

        TestSet testSet = new TestSet(content);
        VingtUnGame game = new VingtUnGame(testSet.getDealer(), testSet.getPunter());
        GameResult result = game.playRound();
        /*
          Original cards:
          - punter: SA + C3 = 13
          - dealer: DA + C2 = 12
          Additional cards:
          - punter: SA + C3 + D7 = 20
          - dealer: DA + C2 + SQ = 22 (greater than 21)
         */

        assertEquals(result.getWinner(), testSet.getPunter());
        assertEquals(result.getLoser(), testSet.getDealer());
    }

    @Test
    public void playRound_noneOfPlayersHasMoreThan21_winnerIsOneWithHigherScore() throws IOException {
        String content = "S3, H6, SJ, HA, HJ, DQ, C6, D9, HK, D10, CJ, H7, S6, S10, C4, C9, D5, C7, HQ, " +
                         "S5, H10, SK, D7, S8, H2, C5, H4, CA, C10, S9, DK, H8, D6, S4, H3, D3, CK, D2, DJ, " +
                         "SQ, S7, CQ, H5, S2, C8, H9, D8, D4, C2, C3, DA, SA\n";

        TestSet testSet = new TestSet(content);
        VingtUnGame game = new VingtUnGame(testSet.getDealer(), testSet.getPunter());
        GameResult result = game.playRound();
        /*
          Original cards:
          - punter: SA + C3 = 13
          - dealer: DA + C2 = 12
          Additional cards:
          - punter: SA + C3 + D4 = 17
          - dealer: DA + C2 + D8 = 20 (greater than 17)
         */

        assertEquals(result.getLoser(), testSet.getPunter());
        assertEquals(result.getWinner(), testSet.getDealer());
    }
}
