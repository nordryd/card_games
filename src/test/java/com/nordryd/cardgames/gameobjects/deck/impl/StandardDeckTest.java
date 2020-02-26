package com.nordryd.cardgames.gameobjects.deck.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import java.util.Random;

import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.Card.Rank;
import com.nordryd.cardgames.gameobjects.Card.Suit;
import com.nordryd.cardgames.gameobjects.deck.Deck;
import com.nordryd.cardgames.gameobjects.deck.exception.DeckException;
import com.nordryd.cardgames.gameobjects.deck.impl.StandardDeck;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * <p>
 * Unit tests for {@link StandardDeck}.
 * </p>
 *
 * @author Nordryd
 */
@RunWith(MockitoJUnitRunner.class)
public class StandardDeckTest
{
    private static final int STD_DECK_SIZE = 52;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Mock
    private Random mockRng;

    @Before
    public void setup() {
        setRandomIntToGenerate(0);
    }

    @Test
    public void testConstructor() {
        assertThat(new StandardDeck(mockRng).remainingCards(), is(STD_DECK_SIZE));
    }

    @Test
    public void testDrawSingle() {
        final int numToGen = 2;
        setRandomIntToGenerate(numToGen);
        final Deck deck = new StandardDeck(mockRng);
        assertThat(deck.draw(), is(Card.get(Rank.FOUR).of(Suit.CLUBS)));
        assertThat(deck.remainingCards(), is(STD_DECK_SIZE - 1));
    }

    @Test
    public void testDrawSingleUsingArgOverload() {
        setRandomIntToGenerate(2);
        final Deck deck = new StandardDeck(mockRng);
        assertThat(deck.draw(1), is(singletonList(Card.get(Rank.FOUR).of(Suit.CLUBS))));
        assertThat(deck.remainingCards(), is(STD_DECK_SIZE - 1));
    }

    @Test
    public void testDrawMultiple() {
        setRandomIntToGenerate(2);
        final Deck deck = new StandardDeck(mockRng);
        final int wantedCards = 3;
        assertThat(deck.draw(wantedCards),
                is(asList(Card.get(Rank.FOUR).of(Suit.CLUBS), Card.get(Rank.FIVE).of(Suit.CLUBS),
                        Card.get(Rank.SIX).of(Suit.CLUBS))));
        assertThat(deck.remainingCards(), is(STD_DECK_SIZE - wantedCards));
    }

    @Test
    public void testDrawMultipleOverdrawLooparound() {
        setRandomIntToGenerate(1);
        final Deck deck = new StandardDeck(true, mockRng);
        final int wantedCards = 3;
        final int initialRemaining = 2;
        final int expectedNetRemaining = 51;
        deck.draw(STD_DECK_SIZE - initialRemaining);
        assertThat(deck.remainingCards(), is(initialRemaining));
        assertThat(deck.draw(wantedCards),
                is(asList(Card.get(Rank.KING).of(Suit.SPADES), Card.get(Rank.ACE).of(Suit.SPADES),
                        Card.get(Rank.TWO).of(Suit.CLUBS))));
        assertThat(deck.remainingCards(), is(expectedNetRemaining));
    }

    @Test
    public void testDrawMultipleOverdrawNoLooparound() {
        thrown.expect(DeckException.class);
        thrown.expectMessage("5 cards were requested, but only 4 remain. Use reset() to reset the deck.");
        final Deck deck = new StandardDeck(false, mockRng);
        final int wantedCards = 5;
        final int initialRemaining = 4;
        deck.draw(STD_DECK_SIZE - initialRemaining);
        assertThat(deck.remainingCards(), is(initialRemaining));
        deck.draw(wantedCards);
    }

    @Test
    public void testDrawMultipleOverdrawNoLooparound1Left() {
        thrown.expect(DeckException.class);
        thrown.expectMessage("2 cards were requested, but only 1 remains. Use reset() to reset the deck.");
        final Deck deck = new StandardDeck(false, mockRng);
        final int wantedCards = 2;
        final int initialRemaining = 1;
        deck.draw(STD_DECK_SIZE - 1);
        assertThat(deck.remainingCards(), is(initialRemaining));
        deck.draw(wantedCards);
    }

    @Test
    public void testDrawMultipleOver52Looparound() {
        final Deck deck = new StandardDeck(true, mockRng);
        final int wantedCards = 53;
        final int expectedNetRemaining = 51;
        assertThat(deck.draw(wantedCards).size(), is(wantedCards));
        assertThat(deck.remainingCards(), is(expectedNetRemaining));
    }

    @Test
    public void testDrawMultipleOver52NoLooparound() {
        thrown.expect(DeckException.class);
        thrown.expectMessage("53 cards were requested when there can be no more than 52.");
        new StandardDeck(false, mockRng).draw(53);
    }

    @Test
    public void testDrawMultipleExactly52Looparound() {
        final Deck deck = new StandardDeck(true, mockRng);
        assertThat(deck.draw(STD_DECK_SIZE).size(), is(STD_DECK_SIZE));
        assertThat(deck.remainingCards(), is(0));
    }

    @Test
    public void testDrawMultipleExactly52NoLooparound() {
        final Deck deck = new StandardDeck(true, mockRng);
        assertThat(deck.draw(STD_DECK_SIZE).size(), is(STD_DECK_SIZE));
        assertThat(deck.remainingCards(), is(0));
    }

    @Test
    public void testDrawDeckIsEmpty() {
        thrown.expect(DeckException.class);
        thrown.expectMessage("There are no more cards to draw! Use reset() to reset the deck.");
        final Deck deck = new StandardDeck(false, mockRng);
        deck.draw(STD_DECK_SIZE);
        deck.draw();
    }

    @Test
    public void testDrawMultipleDeckIsEmpty() {
        thrown.expect(DeckException.class);
        thrown.expectMessage("There are no more cards to draw! Use reset() to reset the deck.");
        final Deck deck = new StandardDeck(false, mockRng);
        deck.draw(STD_DECK_SIZE);
        deck.draw(2);
    }

    @Test
    public void testDrawMultipleZero() {
        thrown.expect(DeckException.class);
        thrown.expectMessage("Cannot draw 0 cards.");
        final Deck deck = new StandardDeck(mockRng);
        deck.draw(0);
    }

    @Test
    public void testDrawMultipleNegative() {
        thrown.expect(DeckException.class);
        thrown.expectMessage("Cannot draw a negative number of cards.");
        final Deck deck = new StandardDeck(mockRng);
        deck.draw(-1);
    }

    private void setRandomIntToGenerate(final int numberToGen) {
        if (numberToGen > 0) {
            when(mockRng.nextInt(intThat(integer -> integer > 0))).thenReturn(numberToGen);
        }
        else {
            when(mockRng.nextInt(intThat(integer -> integer > 0))).thenCallRealMethod();
        }
    }
}
