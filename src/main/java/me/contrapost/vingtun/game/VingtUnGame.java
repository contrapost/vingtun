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
            System.out.println(dealer.getDeck());
        }

        // 2. dealer deals initial cards to the punter and to itself
        dealOriginalCards(punter, dealer);

        // 3. checking the initial score
        GameResult originalResult = getOriginalCardDealResult(punter, dealer);

        if (originalResult != null) return originalResult;

        // 4. checking if the punter has won with additional cards
        dealAddictionCards(punter, PUNTER_ADDITIONAL_CARD_LIMIT);
        int punterFinalScore = getPlayersScore(punter);
        GameResult punterResultWithAdditionalCards = getAdditionalCardResult(punter, dealer, punterFinalScore);

        if (punterResultWithAdditionalCards != null) return punterResultWithAdditionalCards;

        // 5. checking if the dealer has won with additional cards
        dealAddictionCards(dealer, getPlayersScore(punter));
        int dealerFinalScore = getPlayersScore(dealer);
        GameResult dealerResultWithAdditionalCards = getAdditionalCardResult(dealer, punter, dealerFinalScore);

        if (dealerResultWithAdditionalCards != null) return dealerResultWithAdditionalCards;

        // 6. comparing scores to find the winner
        if (punterFinalScore > dealerFinalScore) {
            return new GameResult(punter, dealer);
        } else {
            return new GameResult(dealer, punter);
        }
    }
}
