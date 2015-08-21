package blackjack.game;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Alex Euzent on 8/20/2015.
 */
public class DealerTest extends TestCase {

    @Test
    public void testShowHand() throws Exception {
        boolean result = true;
        Dealer d = new Dealer();
        ArrayList<Card> test = new ArrayList<Card>();
        for(int x = 7; x < 16; x++){
            Card c = new Card(x);
            test.add(c);
            d.hit(c);
        }
        ArrayList<Card> cde = d.showHand();
        for(Card c : cde){
            if(!test.contains(c)){
                result = false;
            }
        }
        assertTrue("Generated cards not appearing in dealer hand",result);
    }

    @Test
    public void testCurrHandSum() throws Exception {
        Card a = new Card(Card.TWO,Card.CLUBS);
        Card b = new Card(Card.ACE,Card.DIAMONDS);
        Card c = new Card(Card.THREE,Card.HEARTS);
        Card d = new Card(Card.ACE,Card.SPADES);
        Dealer dlr = new Dealer();
        dlr.hit(a);
        dlr.hit(b);
        dlr.hit(c);
        dlr.hit(d);
        assertEquals("Expected 17, got " + dlr.currHandSum(),17,dlr.currHandSum());
    }


    @Test
    public void testEndRound() throws Exception {
        Dealer d = new Dealer();
        ArrayList<Card> test = new ArrayList<Card>();
        for(int x = 7; x < 16; x++){
            Card c = new Card(x);
            test.add(c);
            d.hit(c);
        }
        ArrayList<Card> trash = d.endRound();
        ArrayList<Card> out = d.showHand();
        boolean result = false;
        if(trash.size() == 9 && out.size() == 0)
            result = true;
        assertTrue("Dealer hand did not clear properly",result);
    }
}