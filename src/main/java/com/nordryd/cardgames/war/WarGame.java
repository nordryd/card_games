package com.nordryd.cardgames.war;

import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.Card.BattleResult;
import com.nordryd.cardgames.gameobjects.deck.Deck;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * A game of War. (should the game be run and then returned as a whole and cycled through in states, like a sim?)
 * </p>
 *
 * @author Nordryd
 */
public class WarGame
{
    private final int winsRequiredForVictory, player1Wins, player2Wins;

    @Autowired
    private Deck deck;

    private WarGame(final GameParameters gameParameters) {
        this.winsRequiredForVictory = gameParameters.winsRequiredForVictory;
        this.player1Wins = 0;
        this.player2Wins = 0;
    }

    public Pair<BattleResult, Pair<Card, Card>> play() {
        final Card p1Card = deck.draw(), p2Card = deck.draw();
        return new Pair<>(p1Card.battle(p2Card), new Pair<>(p1Card, p2Card));
    }

    /**
     * Readies a new {@link WarGame game of War}.
     *
     * @return a set of {@link GameParameters parameters} to customize the game before starting it.
     */
    public static GameParameters newGame() {
        return new GameParameters();
    }

    /**
     * Parameters for a given game.
     */
    public static final class GameParameters
    {
        private int winsRequiredForVictory = 7;

        /**
         * The number of times a player needs to win in order to achieve overall victory. The default value is 7.
         *
         * @param winsRequiredForVictory the number of wins a player needs to achieve overall victory.
         * @return the current set of {@link GameParameters game parameters}.
         */
        public GameParameters winsRequiredForVictory(final int winsRequiredForVictory) {
            this.winsRequiredForVictory = winsRequiredForVictory;
            return this;
        }

        /**
         * @return a newly started {@link WarGame game of War} with all the desired parameters in place.
         */
        public WarGame start() {
            if (winsRequiredForVictory <= 0) {
                throw new IllegalStateException(
                        "Must be at least 1 win required to win, otherwise there's no game to be played.");
            }
            return new WarGame(this);
        }
    }
}
