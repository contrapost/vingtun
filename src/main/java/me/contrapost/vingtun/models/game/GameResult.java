package me.contrapost.vingtun.models.game;

import me.contrapost.vingtun.models.players.Player;

public class GameResult {

    private final Player winner;
    private final Player loser;

    public GameResult(final Player winner, final Player loser) {
        this.winner = winner;
        this.loser = loser;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }
}
