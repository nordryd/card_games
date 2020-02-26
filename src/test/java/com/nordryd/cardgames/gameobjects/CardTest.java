package com.nordryd.cardgames.gameobjects;

import static com.nordryd.cardgames.gameobjects.Card.BattleResult;
import static com.nordryd.cardgames.gameobjects.Card.Rank;
import static com.nordryd.cardgames.gameobjects.Card.Suit;
import static com.nordryd.cardgames.gameobjects.Card.get;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * <p>
 * Unit tests for {@link Card}.
 * </p>
 *
 * @author Nordryd
 */
public class CardTest
{
    @Test
    public void testBuilder() {
        final Rank expectedRank = Rank.NINE;
        final Suit expectedSuit = Suit.SPADES;
        final Card card = get(expectedRank).of(expectedSuit);
        assertEquals(expectedRank, card.getRank());
        assertEquals(expectedSuit, card.getSuit());
    }

    @Test
    public void testBattle() {
        final Card expectedHigherCard = get(Rank.KING).of(Suit.CLUBS);
        final Card expectedLowerCard = get(Rank.QUEEN).of(Suit.SPADES);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleSameRank() {
        final Card expectedHigherCard = get(Rank.KING).of(Suit.HEARTS);
        final Card expectedLowerCard = get(Rank.KING).of(Suit.DIAMONDS);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleSameRankAcesHigh() {
        final Card expectedHigherCard = get(Rank.ACE).of(Suit.HEARTS);
        final Card expectedLowerCard = get(Rank.ACE).of(Suit.DIAMONDS);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard, true),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard, true),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleSameRankAcesLow() {
        final Card expectedHigherCard = get(Rank.ACE).of(Suit.HEARTS);
        final Card expectedLowerCard = get(Rank.ACE).of(Suit.DIAMONDS);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard, false),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard, false),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleSameSuit() {
        final Card expectedHigherCard = get(Rank.KING).of(Suit.SPADES);
        final Card expectedLowerCard = get(Rank.QUEEN).of(Suit.SPADES);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleAcesHigh() {
        final Card expectedHigherCard = get(Rank.ACE).of(Suit.CLUBS);
        final Card expectedLowerCard = get(Rank.TWO).of(Suit.SPADES);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard, true),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard, true),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleAcesLow() {
        final Card expectedHigherCard = get(Rank.TWO).of(Suit.CLUBS);
        final Card expectedLowerCard = get(Rank.ACE).of(Suit.SPADES);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard, false),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard, false),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleAcesHighSameSuit() {
        final Card expectedHigherCard = get(Rank.ACE).of(Suit.SPADES);
        final Card expectedLowerCard = get(Rank.QUEEN).of(Suit.SPADES);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard, true),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard, true),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleAcesLowSameSuit() {
        final Card expectedHigherCard = get(Rank.TWO).of(Suit.CLUBS);
        final Card expectedLowerCard = get(Rank.ACE).of(Suit.CLUBS);
        assertEquals(BattleResult.WIN, expectedHigherCard.battle(expectedLowerCard, false),
                getBattleString(expectedHigherCard, expectedLowerCard));
        assertEquals(BattleResult.LOSE, expectedLowerCard.battle(expectedHigherCard, false),
                getBattleString(expectedLowerCard, expectedHigherCard));
    }

    @Test
    public void testBattleTie() {
        final Card card = get(Rank.EIGHT).of(Suit.CLUBS);
        final Card otherCard = get(Rank.EIGHT).of(Suit.CLUBS);
        assertEquals(BattleResult.TIE, card.battle(otherCard), getBattleString(card, otherCard));
        assertEquals(BattleResult.TIE, otherCard.battle(card), getBattleString(otherCard, card));
    }

    @Test
    public void testBattleTieAcesHigh() {
        final Card card = get(Rank.ACE).of(Suit.CLUBS);
        final Card otherCard = get(Rank.ACE).of(Suit.CLUBS);
        assertEquals(BattleResult.TIE, card.battle(otherCard, true), getBattleString(card, otherCard));
        assertEquals(BattleResult.TIE, otherCard.battle(card, true), getBattleString(otherCard, card));
    }

    @Test
    public void testBattleTieAcesLow() {
        final Card card = get(Rank.ACE).of(Suit.CLUBS);
        final Card otherCard = get(Rank.ACE).of(Suit.CLUBS);
        assertEquals(BattleResult.TIE, card.battle(otherCard, false), getBattleString(card, otherCard));
        assertEquals(BattleResult.TIE, otherCard.battle(card, false), getBattleString(otherCard, card));
    }

    @Test
    public void testToString() {
        assertEquals("JACK of HEARTS", get(Rank.JACK).of(Suit.HEARTS).toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        final Card baseCard = get(Rank.TWO).of(Suit.CLUBS);
        final Card cardSame = get(Rank.TWO).of(Suit.CLUBS);
        final Card cardDiffRank = get(Rank.THREE).of(Suit.CLUBS);
        final Card cardDiffSuit = get(Rank.TWO).of(Suit.SPADES);
        final Card cardCompletelyDiff = get(Rank.THREE).of(Suit.SPADES);
        assertEquals(baseCard, cardSame);
        assertEquals(cardSame, baseCard);
        assertEquals(baseCard.hashCode(), cardSame.hashCode());
        assertNotEquals(baseCard, cardDiffRank);
        assertNotEquals(baseCard, cardDiffSuit);
        assertNotEquals(baseCard, cardCompletelyDiff);
    }

    private static String getBattleString(final Card attacking, final Card defending) {
        return format("BATTLE[%s -> %s]", attacking, defending);
    }
}
