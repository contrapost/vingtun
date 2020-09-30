package me.contrapost.vingtun;

import me.contrapost.vingtun.game.VingtUnGame;
import me.contrapost.vingtun.models.cards.Card;
import me.contrapost.vingtun.models.cards.Deck;
import me.contrapost.vingtun.models.game.GameResult;
import me.contrapost.vingtun.models.players.Dealer;
import me.contrapost.vingtun.models.players.Punter;

import java.util.List;
import java.util.stream.Collectors;

import static me.contrapost.vingtun.util.CLIProcessor.getFileNameFromCLI;

public class VingtUnApp {

    public static void main(final String[] args) {
        // 1. initiating a deck (from a file or a new one)
        String deckFileName = getFileNameFromCLI(args);
        Deck deck = new Deck(deckFileName);
        if (null == deckFileName) {
            System.out.println("New deck of cards was initialized.");
        }
        // 2. creating players
        Dealer dealer = new Dealer(deck);
        Punter punter = new Punter(dealer, "sam");

        // 3. initiating and running the game
        VingtUnGame game = new VingtUnGame(dealer, punter);
        GameResult result = game.playRound();

        // 4. printing results
        printResults(result);
    }

    private static void printResults(final GameResult result) {
        System.out.printf("[%s]%n", result.getWinner().getName());
        System.out.printf("%s: %s%n", result.getWinner().getName(), cardsAsString(result.getWinner().getReceivedCards()));
        System.out.printf("%s: %s%n", result.getLoser().getName(), cardsAsString(result.getLoser().getReceivedCards()));
    }

    private static String cardsAsString(List<Card> cards) {
        return cards.stream().map(Card::toString).collect(Collectors.joining(", "));
    }
}
