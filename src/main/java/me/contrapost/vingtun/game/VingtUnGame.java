package me.contrapost.vingtun.game;

import me.contrapost.vingtun.models.game.GameResult;
import me.contrapost.vingtun.models.players.Dealer;
import me.contrapost.vingtun.models.players.Punter;

import java.util.Optional;

import static me.contrapost.vingtun.game.util.VingtUnConstants.PUNTER_ADDITIONAL_CARD_LIMIT;
import static me.contrapost.vingtun.game.util.VingtUnUtil.*;

/**
 * Class for creating an instance of Vingt Un (21) game
 * Instance is created for a dealer and one player (punter)
 */
public class VingtUnGame {

    private final Dealer dealer;
    private final Punter punter;

    public VingtUnGame(final Dealer dealer, final Punter punter) {
        this.dealer = dealer;
        this.punter = punter;
    }

    /**
     * Run a round of the game implementing logic mentioned in README.md
     * @see <a href="https://github.com/contrapost/vingtun#intro">https://github.com/contrapost/vingtun</a>
     *
     * @return  {@link GameResult} with winner and loser of type {@link me.contrapost.vingtun.models.players.Player}
     */
    public GameResult playRound() {
        // 1. Dealer has to shuffle the deck if it is new (not from a file)
        if (!dealer.getDeck().isShuffled()) {
            dealer.shuffleDeck();
        }

        // 2. dealer deals initial cards to the punter and to itself
        dealOriginalCards(punter, dealer);

        // 3. checking the initial score
        Optional<GameResult> originalResult = getOriginalCardDealResult(punter, dealer);

        if (originalResult.isPresent()) {
            return originalResult.get();
        }

        // 4. checking if the punter has won with additional cards
        dealAddictionCards(punter, PUNTER_ADDITIONAL_CARD_LIMIT);
        Optional<GameResult> gameResultWithPuntersAdditionalCards = getResultWithAdditionalCards(punter, dealer);

        if (gameResultWithPuntersAdditionalCards.isPresent()) {
            return gameResultWithPuntersAdditionalCards.get();
        }

        // 5. checking if the dealer has won with additional cards
        dealAddictionCards(dealer, getPlayersScore(punter));
        Optional<GameResult> gameResultWithDealersAdditionalCards = getResultWithAdditionalCards(dealer, punter);

        if (gameResultWithDealersAdditionalCards.isPresent()) {
            return gameResultWithDealersAdditionalCards.get();
        }

        // 6. comparing scores to find the winner
        // could be simplified to return _new GameResult(dealer, punter)_ because dealer stopped to draw cards
        // in previous step (5) when its score became higher than punter's,
        // if dealer's score is greater than 21 it lost the game, if not his score is always greater than punter's
        if (getPlayersScore(punter) > getPlayersScore(dealer)) {
            // we cannot reach this (but could be useful to have anyway for readability or future extension of the game
            // for several punters
            return new GameResult(punter, dealer);
        } else {
            return new GameResult(dealer, punter);
        }
    }
}
