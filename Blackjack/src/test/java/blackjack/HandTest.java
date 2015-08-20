package blackjack;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex Euzent on 8/20/2015.
 */
public class HandTest extends TestCase {

    @Test
    public void testShowCards() throws Exception {
        boolean result = true;
        ArrayList<Card> test = new ArrayList<Card>();
        Hand h = new Hand();
        for(int x = 6; x < 12; x++) {
            Card c = new Card(x);
            test.add(c);
            h.addCard(c);
        }
        ArrayList<Card> out = h.showCards();
        for(Card d : out){
            if(!test.contains(d)){
                result = false;
            }
        }
        assertTrue("Not all cards in hand",result);
    }

    @Test
    public void testClearHand() throws Exception {
        Hand h = new Hand();
        for(int x = 6; x < 12; x++) {
            Card c = new Card(x);
            h.addCard(c);
        }
        h.clearHand();
        ArrayList<Card> out = h.showCards();
        assertEquals("Not all cards in hand",0, out.size());
    }

    @Test
    public void testGetHandSum() throws Exception {
        Card a = new Card(Card.EIGHT,Card.CLUBS);
        Card b = new Card(Card.ACE,Card.DIAMONDS);
        Card c = new Card(Card.KING,Card.HEARTS);
        Card d = new Card(Card.ACE,Card.HEARTS);
        Hand h = new Hand();
        h.addCard(a);
        h.addCard(b);
        h.addCard(c);
        h.addCard(d);
        int sum = h.getHandSum();
        assertEquals("Expected sum of 20, got " + sum,20,sum);
    }
}