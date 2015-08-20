package blackjack;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alex Euzent on 8/19/2015.
 */
public class CardTest extends TestCase {

    @Test
    public void testGetRank() throws Exception {
        Card c = new Card(24);
        assertEquals("Expected rank = 11, got " + c.getRank(),Card.QUEEN,c.getRank());
    }

    @Test
    public void testGetSuit() throws Exception {
        Card c = new Card(15);
        assertEquals("Expected suit = 11, got " + c.getSuit(), Card.DIAMONDS, c.getSuit());
    }

    @Test
    public void testToString() throws Exception {
        Card c = new Card(48);
        assertTrue("Expected \"Ten of Spades\", got " + c.toString(), c.toString().equals("Ten of Spades"));

        Card d = new Card(Card.SIX, Card.CLUBS);
        assertTrue("Expected \"Six of Clubs\", got " + d.toString(), d.toString().equals("Six of Clubs"));
    }

    @Test
    public void testInvalidCards() throws Exception {
        boolean thrown = false;
        try {
            Card c = new Card(53);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("53 tested as valid and should not have",thrown);
        thrown = false;
        try {
            Card c = new Card(-2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("-2 tested as valid and should not have",thrown);
    }


    @Test
    public void testInvalidRank() throws Exception {
        boolean thrown = false;
        try {
            Card c = new Card(-1, Card.DIAMONDS);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("Rank of -1 tested as valid and should not have",thrown);
        thrown = false;
        try {
            Card c = new Card(15, Card.DIAMONDS);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("Rank of 15 tested as valid and should not have",thrown);
    }

    @Test
    public void testInvalidSuit() throws Exception {
        boolean thrown = false;
        try {
            Card c = new Card(Card.SEVEN, -1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("Suit of -1 tested as valid and should not have",thrown);
        thrown = false;
        try {
            Card c = new Card(Card.EIGHT, 6);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("Suit of 6 tested as valid and should not have",thrown);
    }
}