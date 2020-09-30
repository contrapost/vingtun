package me.contrapost.vingtun;

import me.contrapost.vingtun.models.cards.Deck;
import me.contrapost.vingtun.models.players.Dealer;
import me.contrapost.vingtun.models.players.Punter;

import java.io.IOException;

import static me.contrapost.vingtun.FileTestUtil.createFile;

public class TestSet {

    private final Dealer dealer;
    private final Punter punter;

    public TestSet(final String cards) throws IOException {
        final Deck deck;
        if (null == cards) {
            deck = new Deck(null);
        } else {
            String fileName = createFile(cards);
            deck = new Deck(fileName);
        }


        dealer = new Dealer(deck);
        punter = new Punter(dealer, "test");
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Punter getPunter() {
        return punter;
    }
}
