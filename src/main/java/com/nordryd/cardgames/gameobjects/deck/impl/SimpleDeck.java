package com.nordryd.cardgames.gameobjects.deck.impl;

import static com.nordryd.cardgames.gameobjects.Card.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Random;

import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.deck.Deck;

/**
 * <p>
 * A {@link Deck} that has no smart way of how it draws. It just gives a random card of all possible 52, and infinitely many times.
 * </p>
 *
 * @author Nordryd
 */
public class SimpleDeck implements Deck
{
    private final Random rng;

    /**
     * Constructor.
     *
     * @param rng a {@link Random random number generator}.
     */
    public SimpleDeck(final Random rng) {
        this.rng = rng;
    }

    @Override
    public Card draw() {
        return get(Rank.values()[rng.nextInt(Rank.values().length)])
                .of(Suit.values()[rng.nextInt(Suit.values().length)]);
    }

    @Override
    public List<Card> draw(final int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Cannot draw 0 cards.");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("Cannot draw a negative number of cards.");
        }

        return range(0, amount).mapToObj(iteration -> draw()).collect(toList());
    }

    @Override
    public int remainingCards() {
        return Integer.MAX_VALUE;
    }
}
