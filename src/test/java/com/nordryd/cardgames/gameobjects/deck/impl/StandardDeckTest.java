package com.nordryd.cardgames.gameobjects.deck.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;

import java.util.Random;

import com.nordryd.cardgames.gameobjects.Card;
import com.nordryd.cardgames.gameobjects.Card.Rank;
import com.nordryd.cardgames.gameobjects.Card.Suit;
import com.nordryd.cardgames.gameobjects.deck.Deck;
import com.nordryd.cardgames.gameobjects.deck.exception.DeckException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * <p>
 * Unit tests for {@link StandardDeck}.
 * </p>
 *
 * @author Nordryd
 */
@ExtendWith(MockitoExtension.class)
public class StandardDeckTest
{
    private static final int STD_DECK_SIZE = 52;

    @Mock
    private Random mockRng;

    @Test
    public void testConstructor() {
        assertEquals(STD_DECK_SIZE, new StandardDeck(mockRng).remainingCards());
    }

    @Test
    public void testDrawSingle() {
        final int numToGen = 2;
        setRandomIntToGenerate(numToGen);
        final Deck deck = new StandardDeck(mockRng);
        assertEquals(Card.get(Rank.FOUR).of(Suit.CLUBS), deck.draw());
        assertEquals(STD_DECK_SIZE - 1, deck.remainingCards());
    }

    @Test
    public void testDrawSingleUsingArgOverload() {
        setRandomIntToGenerate(2);
        final Deck deck = new StandardDeck(mockRng);
        assertEquals(singletonList(Card.get(Rank.FOUR).of(Suit.CLUBS)), deck.draw(1));
        assertEquals(STD_DECK_SIZE - 1, deck.remainingCards());
    }

    @Test
    public void testDrawMultiple() {
        setRandomIntToGenerate(2);
        final Deck deck = new StandardDeck(mockRng);
        final int wantedCards = 3;
        assertEquals(asList(Card.get(Rank.FOUR).of(Suit.CLUBS), Card.get(Rank.FIVE).of(Suit.CLUBS),
                Card.get(Rank.SIX).of(Suit.CLUBS)), deck.draw(wantedCards));
        assertEquals(STD_DECK_SIZE - wantedCards, deck.remainingCards());
    }

    @Test
    public void testDrawMultipleOverdrawLooparound() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(true, mockRng);
        final int wantedCards = 5;
        final int initialRemaining = 4;
        final int expectedNetRemaining = 51;
        deck.draw(STD_DECK_SIZE - initialRemaining);
        assertEquals(initialRemaining, deck.remainingCards());
        assertEquals(asList(Card.get(Rank.JACK).of(Suit.SPADES), Card.get(Rank.QUEEN).of(Suit.SPADES),
                Card.get(Rank.KING).of(Suit.SPADES), Card.get(Rank.ACE).of(Suit.SPADES),
                Card.get(Rank.TWO).of(Suit.CLUBS)), deck.draw(wantedCards));
        assertEquals(expectedNetRemaining, deck.remainingCards());
    }

    @Test
    public void testDrawMultipleOverdrawNoLooparound() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(false, mockRng);
        final int wantedCards = 5;
        final int initialRemaining = 4;
        deck.draw(STD_DECK_SIZE - initialRemaining);
        assertEquals(initialRemaining, deck.remainingCards());
        assertEquals("5 cards were requested, but only 4 remain. Use reset() to reset the deck.",
                assertThrows(DeckException.class, () -> deck.draw(wantedCards)).getMessage());
    }

    @Test
    public void testDrawMultipleOverdrawNoLooparound1Left() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(false, mockRng);
        final int wantedCards = 2;
        final int initialRemaining = 1;
        deck.draw(STD_DECK_SIZE - initialRemaining);
        assertEquals(initialRemaining, deck.remainingCards());
        assertEquals("2 cards were requested, but only 1 remains. Use reset() to reset the deck.",
                assertThrows(DeckException.class, () -> deck.draw(wantedCards)).getMessage());
    }

    @Test
    public void testDrawMultipleOver52Looparound() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(true, mockRng);
        final int wantedCards = 53;
        final int expectedNetRemaining = 51;
        assertEquals(wantedCards, deck.draw(wantedCards).size());
        assertEquals(expectedNetRemaining, deck.remainingCards());
    }

    @Test
    public void testDrawMultipleOver52NoLooparound() {
        assertEquals("53 cards were requested when there can be no more than 52.",
                assertThrows(DeckException.class, () -> new StandardDeck(false, mockRng).draw(53)).getMessage());
    }

    @Test
    public void testDrawMultipleExactly52Looparound() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(true, mockRng);
        assertEquals(STD_DECK_SIZE, deck.draw(STD_DECK_SIZE).size());
        assertEquals(0, deck.remainingCards());
    }

    @Test
    public void testDrawMultipleExactly52NoLooparound() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(true, mockRng);
        assertEquals(STD_DECK_SIZE, deck.draw(STD_DECK_SIZE).size());
        assertEquals(0, deck.remainingCards());
    }

    @Test
    public void testDrawDeckIsEmpty() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(false, mockRng);
        deck.draw(STD_DECK_SIZE);
        assertEquals("There are no more cards to draw! Use reset() to reset the deck.",
                assertThrows(DeckException.class, deck::draw).getMessage());
    }

    @Test
    public void testDrawMultipleDeckIsEmpty() {
        setRandomIntToGenerate(0);
        final Deck deck = new StandardDeck(false, mockRng);
        deck.draw(STD_DECK_SIZE);
        assertEquals("There are no more cards to draw! Use reset() to reset the deck.",
                assertThrows(DeckException.class, () -> deck.draw(2)).getMessage());
    }

    @Test
    public void testDrawMultipleZero() {
        assertEquals("Cannot draw 0 cards.",
                assertThrows(DeckException.class, () -> new StandardDeck(mockRng).draw(0)).getMessage());
    }

    @Test
    public void testDrawMultipleNegative() {
        assertEquals("Cannot draw a negative number of cards.",
                assertThrows(DeckException.class, () -> new StandardDeck(mockRng).draw(-1)).getMessage());
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
