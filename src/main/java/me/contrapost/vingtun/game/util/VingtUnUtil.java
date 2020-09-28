package me.contrapost.vingtun.game.util;

import me.contrapost.vingtun.models.game.GameResult;
import me.contrapost.vingtun.models.players.Player;

import static me.contrapost.vingtun.game.util.VingtUnConstants.ORIGINAL_SCORE_DEALER_WIN;
import static me.contrapost.vingtun.game.util.VingtUnConstants.WIN_SCORE;

public class VingtUnUtil {

    private VingtUnUtil() {}

    public static int getPlayersScore(final Player player) {
        return player.getReceivedCards().stream().mapToInt(card -> card.getCardValue().getValue()).sum();
    }

    public static GameResult getOriginalCardDealResult(final Player punter,
                                                 final Player dealer) {
        int punterOriginalScore = getPlayersScore(punter);
        int dealerOriginalScore = getPlayersScore(dealer);

        boolean punterHasBlackJack = hasBlackJack(punterOriginalScore);
        boolean dealerHasBlackJack = hasBlackJack(dealerOriginalScore);

        boolean punterHas22 = has22(punterOriginalScore);
        boolean dealerHas22 = has22(dealerOriginalScore);

        if (punterHasBlackJack) {
            return new GameResult(punter, dealer);
        } else if (dealerHasBlackJack) {
            return new GameResult(dealer, punter);
        } else if (punterHas22 && dealerHas22) {
            return new GameResult(dealer, punter);
        } else {
            return null;
        }
    }

    public static void dealAddictionCards(final Player player, final int limit) {
        while (player.canDrawCard(limit)) {
            player.receiveCards(1);
        }
    }

    public static GameResult getAdditionalCardResult(final Player player, final Player opponent, final int playerScore) {
        if (playerScore > WIN_SCORE) {
            return new GameResult(opponent, player);
        } else {
            return null;
        }
    }

    public static void dealOriginalCards(final Player punter, final Player dealer) {
        punter.receiveCards(1);
        dealer.receiveCards(1);
        punter.receiveCards(1);
        dealer.receiveCards(1);
    }

    private static boolean has22(final int score) {
        return score == ORIGINAL_SCORE_DEALER_WIN;
    }

    private static boolean hasBlackJack(final int score) {
        return score == WIN_SCORE;
    }
}
