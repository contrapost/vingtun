package me.contrapost.vingtun.game;

import me.contrapost.vingtun.models.game.GameResult;
import me.contrapost.vingtun.models.players.Dealer;
import me.contrapost.vingtun.models.players.Punter;

import static me.contrapost.vingtun.game.util.VingtUnConstants.PUNTER_ADDITIONAL_CARD_LIMIT;
import static me.contrapost.vingtun.game.util.VingtUnUtil.*;

public class VingtUnGame {

    private final Dealer dealer;
    private final Punter punter;

    public VingtUnGame(final Dealer dealer, final Punter punter) {
        this.dealer = dealer;
        this.punter = punter;
    }

    public GameResult playRound() {
        // 1. Dealer has to shuffle the deck if it is new (not from a file)
        if (!dealer.getDeck().isShuffled()) {
            dealer.shuffleDeck();
        }

        // 2. dealer deals initial cards to the punter and to itself
        dealOriginalCards(punter, dealer);

        // 3. checking the initial score
        GameResult originalResult = getOriginalCardDealResult(punter, dealer);

        if (originalResult != null) return originalResult;

        // 4. checking if the punter has won with additional cards
        dealAddictionCards(punter, PUNTER_ADDITIONAL_CARD_LIMIT);
        int punterFinalScore = getPlayersScore(punter);
        GameResult gameResultWithPuntersAdditionalCards = getAdditionalCardResult(punter, dealer, punterFinalScore);

        if (gameResultWithPuntersAdditionalCards != null) return gameResultWithPuntersAdditionalCards;

        // 5. checking if the dealer has won with additional cards
        dealAddictionCards(dealer, getPlayersScore(punter));
        int dealerFinalScore = getPlayersScore(dealer);
        GameResult gameResultWithDealerAdditionalCards = getAdditionalCardResult(dealer, punter, dealerFinalScore);

        if (gameResultWithDealerAdditionalCards != null) return gameResultWithDealerAdditionalCards;

        // 6. comparing scores to find the winner
        // could be simplified to return _new GameResult(dealer, punter)_ because dealer stopped to draw cards
        // in previous step (5) when its score became higher than punter's,
        // if dealer's score is greater than 21 it lost the game, if not his score is always greater than punter's
        if (punterFinalScore > dealerFinalScore) {
            // we cannot reach this (but could be useful to have anyway for readability or future extension of the game
            // for several punters
            return new GameResult(punter, dealer);
        } else {
            return new GameResult(dealer, punter);
        }
    }
}
