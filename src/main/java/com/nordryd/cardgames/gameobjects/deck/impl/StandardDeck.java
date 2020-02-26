package com.nordryd.cardgames.gameobjects.deck.impl;

import static com.nordryd.cardgames.gameobjects.Card.Rank;
import static com.nordryd.cardgames.gameobjects.Card.Suit;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.springframework.util.CollectionUtils.isEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.deck.Deck;
import com.nordryd.cardgames.gameobjects.deck.exception.DeckException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * A standard 52-{@link Card card} {@link Deck deck}. This deck will cycle through all 52 cards at least one
 * </p>
 *
 * @author Nordryd
 */
public class StandardDeck implements Deck
{
    private static final List<Card> ALL_POSSIBLE_CARDS;
    private static final int STD_DECK_SIZE;

    private final Random rng;
    private final boolean looparoundWhenEmpty;
    private final List<Card> cards;

    /**
     * Constructor.
     *
     * @param looparoundWhenEmpty <ul>
     * <li>{@code true} - the deck will reset automatically when the last card is drawn and proceed drawing as normal.</li>
     * <li>{@code false} - the deck will remain empty when the last card is drawn and must be manually reset with {@link StandardDeck#reset()}.</li>
     * </ul>
     * @param rng a {@link Random random number generator}.
     */
    public StandardDeck(final boolean looparoundWhenEmpty, final Random rng) {
        this.looparoundWhenEmpty = looparoundWhenEmpty;
        this.rng = rng;
        this.cards = new ArrayList<>();
        reset();
    }

    /**
     * Constructor. When there are no more remaining cards to draw, the deck will automatically reset when the last card and proceed drawing cards as normal.
     *
     * @param rng a {@link Random random number generator}.
     */
    @Autowired
    public StandardDeck(final Random rng) {
        this(true, rng);
    }

    public Card draw() {
        if (isEmpty(cards)) {
            if (looparoundWhenEmpty) {
                reset();
            }
            else {
                throw new DeckException("There are no more cards to draw! Use reset() to reset the deck.");
            }
        }

        final Card card = cards.get(rng.nextInt(cards.size()));
        cards.remove(card);
        return card;
    }

    @Override
    public List<Card> draw(final int amount) {
        if (amount == 0) {
            throw new DeckException("Cannot draw 0 cards.");
        }

        if (amount < 0) {
            throw new DeckException("Cannot draw a negative number of cards.");
        }

        if (!looparoundWhenEmpty) {
            if (isEmpty(cards)) {
                throw new DeckException("There are no more cards to draw! Use reset() to reset the deck.");
            }

            if (amount > STD_DECK_SIZE) {
                throw new DeckException("53 cards were requested when there can be no more than 52.");
            }

            if (amount > cards.size()) {
                throw new DeckException(
                        format("%d cards were requested, but only %s. Use reset() to reset the deck.", amount,
                                (cards.size() == 1) ? "1 remains" : cards.size() + " remain"));
            }
        }
        return range(0, amount).mapToObj(iteration -> draw()).filter(Objects::nonNull).collect(toList());
    }

    @Override
    public int remainingCards() {
        return cards.size();
    }

    /**
     * Resets the deck back to 52 cards.
     */
    public void reset() {
        cards.clear();
        cards.addAll(ALL_POSSIBLE_CARDS);
    }

    static {
        //@formatter:off
        ALL_POSSIBLE_CARDS = asList(
                Card.get(Rank.TWO).of(Suit.CLUBS),
                Card.get(Rank.THREE).of(Suit.CLUBS),
                Card.get(Rank.FOUR).of(Suit.CLUBS),
                Card.get(Rank.FIVE).of(Suit.CLUBS),
                Card.get(Rank.SIX).of(Suit.CLUBS),
                Card.get(Rank.SEVEN).of(Suit.CLUBS),
                Card.get(Rank.EIGHT).of(Suit.CLUBS),
                Card.get(Rank.NINE).of(Suit.CLUBS),
                Card.get(Rank.TEN).of(Suit.CLUBS),
                Card.get(Rank.JACK).of(Suit.CLUBS),
                Card.get(Rank.QUEEN).of(Suit.CLUBS),
                Card.get(Rank.KING).of(Suit.CLUBS),
                Card.get(Rank.ACE).of(Suit.CLUBS),
                Card.get(Rank.TWO).of(Suit.HEARTS),
                Card.get(Rank.THREE).of(Suit.HEARTS),
                Card.get(Rank.FOUR).of(Suit.HEARTS),
                Card.get(Rank.FIVE).of(Suit.HEARTS),
                Card.get(Rank.SIX).of(Suit.HEARTS),
                Card.get(Rank.SEVEN).of(Suit.HEARTS),
                Card.get(Rank.EIGHT).of(Suit.HEARTS),
                Card.get(Rank.NINE).of(Suit.HEARTS),
                Card.get(Rank.TEN).of(Suit.HEARTS),
                Card.get(Rank.JACK).of(Suit.HEARTS),
                Card.get(Rank.QUEEN).of(Suit.HEARTS),
                Card.get(Rank.KING).of(Suit.HEARTS),
                Card.get(Rank.ACE).of(Suit.HEARTS),
                Card.get(Rank.TWO).of(Suit.DIAMONDS),
                Card.get(Rank.THREE).of(Suit.DIAMONDS),
                Card.get(Rank.FOUR).of(Suit.DIAMONDS),
                Card.get(Rank.FIVE).of(Suit.DIAMONDS),
                Card.get(Rank.SIX).of(Suit.DIAMONDS),
                Card.get(Rank.SEVEN).of(Suit.DIAMONDS),
                Card.get(Rank.EIGHT).of(Suit.DIAMONDS),
                Card.get(Rank.NINE).of(Suit.DIAMONDS),
                Card.get(Rank.TEN).of(Suit.DIAMONDS),
                Card.get(Rank.JACK).of(Suit.DIAMONDS),
                Card.get(Rank.QUEEN).of(Suit.DIAMONDS),
                Card.get(Rank.KING).of(Suit.DIAMONDS),
                Card.get(Rank.ACE).of(Suit.DIAMONDS),
                Card.get(Rank.TWO).of(Suit.SPADES),
                Card.get(Rank.THREE).of(Suit.SPADES),
                Card.get(Rank.FOUR).of(Suit.SPADES),
                Card.get(Rank.FIVE).of(Suit.SPADES),
                Card.get(Rank.SIX).of(Suit.SPADES),
                Card.get(Rank.SEVEN).of(Suit.SPADES),
                Card.get(Rank.EIGHT).of(Suit.SPADES),
                Card.get(Rank.NINE).of(Suit.SPADES),
                Card.get(Rank.TEN).of(Suit.SPADES),
                Card.get(Rank.JACK).of(Suit.SPADES),
                Card.get(Rank.QUEEN).of(Suit.SPADES),
                Card.get(Rank.KING).of(Suit.SPADES),
                Card.get(Rank.ACE).of(Suit.SPADES)
        );
        //@formatter:on
        STD_DECK_SIZE = ALL_POSSIBLE_CARDS.size();
    }
}
