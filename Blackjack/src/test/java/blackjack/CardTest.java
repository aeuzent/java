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
    }

    @Test
    public void testInvalidHiCards() throws Exception {
        boolean thrown = false;
        try {
            Card c = new Card(53);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testInvalidLoCards() throws Exception {
        boolean thrown = false;
        try {
            Card c = new Card(-2);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}