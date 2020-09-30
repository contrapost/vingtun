package me.contrapost.vingtun.game.util;

import me.contrapost.vingtun.models.game.GameResult;
import me.contrapost.vingtun.models.players.Player;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

import static me.contrapost.vingtun.game.util.VingtUnConstants.ORIGINAL_SCORE_DEALER_WIN;
import static me.contrapost.vingtun.game.util.VingtUnConstants.WIN_SCORE;

/**
 * Util methods used in game logic:
 * 1. calculation of player's score
 * 2. dealing of original cards
 * 3. determination if result after original card deal
 * 4. additional card deal
 * 5. determination of result with additional cards
 */
public class VingtUnUtil {

    private VingtUnUtil() {}

    public static int getPlayersScore(@Nonnull final Player player) {
        return player.getReceivedCards().stream().mapToInt(card -> card.getCardValue().getValue()).sum();
    }

    public static void dealOriginalCards(final Player punter, final Player dealer) {
        punter.receiveCards(1);
        dealer.receiveCards(1);
        punter.receiveCards(1);
        dealer.receiveCards(1);
    }

    /**
     * Checks if winner can be determined after the original card deal
     * 1. Punter wins if it has BlackJack(21) or both punter and dealer have BlackJack
     * 2. Dealer wins if only it has BlackJack or if both players have 22
     *
     * @param punter    punter
     * @param dealer    dealer
     * @return          {@link Optional} that is empty if winner wasn't determined or {@link GameResult} that contains
     *                  winner and loser {@link Player}s
     */
    public static Optional<GameResult> getOriginalCardDealResult(final Player punter, final Player dealer) {
        GameResult result;
        // first condition covers two cases: (1) both punter and dealer have BlackJack and (2) only punter has BlackJack
        if (hasBlackJack(punter)) {
            result = new GameResult(punter, dealer);
        } else if (hasBlackJack(dealer) || allPlayersHave22(punter, dealer)) {
            result = new GameResult(dealer, punter);
        } else {
            result = null;
        }

        return result == null ? Optional.empty() : Optional.of(result);
    }

    public static void dealAddictionCards(@Nonnull final Player player, final int limit) {
        while (player.canDrawCard(limit)) {
            player.receiveCards(1);
        }
    }

    /**
     * Checks if a player has more than {@link VingtUnConstants#WIN_SCORE} (21). If yes, opponent wins, else winner is
     * not determined.
     *
     * @param player            player of type {@link Player}
     * @param opponent          opponent of type {@link Player}
     * @return                  {@link Optional} that is empty if winner wasn't determined or {@link GameResult} that
     *                          contains winner and loser {@link Player}s
     */
    public static Optional<GameResult> getResultWithAdditionalCards(final Player player,
                                                                    final Player opponent) {
        return getPlayersScore(player) > WIN_SCORE ? Optional.of(new GameResult(opponent, player)) : Optional.empty();
    }

    private static boolean allPlayersHave22(Player... players) {
        return Arrays.stream(players).allMatch(VingtUnUtil::has22);
    }

    private static boolean has22(final Player player) {
        return getPlayersScore(player) == ORIGINAL_SCORE_DEALER_WIN;
    }

    private static boolean hasBlackJack(final Player player) {
        return getPlayersScore(player) == WIN_SCORE;
    }
}
