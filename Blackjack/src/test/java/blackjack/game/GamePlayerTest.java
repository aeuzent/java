package blackjack.game;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex Euzent on 8/20/2015.
 */
public class GamePlayerTest extends TestCase {

    @Test
    public void testShowHand() throws Exception {
        boolean result = true;
        GamePlayer gp = new GamePlayer();
        ArrayList<Card> test = new ArrayList<Card>();
        for(int x = 7; x < 16; x++){
            Card c = new Card(x);
            test.add(c);
            gp.hit(c);
        }
        ArrayList<Card> cde = gp.showHand();
        for(Card c : cde){
            if(!test.contains(c)){
                result = false;
            }
        }
        assertTrue("Generated cards not appearing in player hand", result);
    }

    @Test
    public void testCurrHandSum() throws Exception {
        Card a = new Card(Card.SIX,Card.HEARTS);
        Card b = new Card(Card.KING,Card.CLUBS);
        Card c = new Card(Card.ACE,Card.SPADES);
        Card d = new Card(Card.TWO,Card.SPADES);
        Dealer dlr = new Dealer();
        dlr.hit(a);
        dlr.hit(b);
        dlr.hit(c);
        dlr.hit(d);
        assertEquals("Expected 19, got " + dlr.currHandSum(),19,dlr.currHandSum());
    }

    @Test
    public void testGetScore() throws Exception {
        GamePlayer gp = new GamePlayer(153);
        assertEquals("Expected 153, got " + gp.getScore(),153.0,gp.getScore());
    }

    @Test
    public void testGetCurrentBet() throws Exception {
        GamePlayer gp = new GamePlayer();
        assertEquals("Expected 5, got " + gp.getCurrentBet(), 5, gp.getCurrentBet());
        gp.increaseBet();
        gp.increaseBet();
        assertEquals("Expected 15, got " + gp.getCurrentBet(), 15, gp.getCurrentBet());
    }

    @Test
    public void testGetActiveBet() throws Exception {
        GamePlayer gp = new GamePlayer();
        double score = gp.getScore();
        assertEquals("Expected 0, got" + gp.getActiveBet(), 0, gp.getActiveBet());
        assertEquals("Score did not remove bet", score - gp.getActiveBet(), gp.getScore());
        gp.increaseBet();
        gp.commitBet();
        assertEquals("Expected 10, got" + gp.getActiveBet(), 10, gp.getActiveBet());
        assertEquals("Score did not remove bet", score - gp.getActiveBet(), gp.getScore());
    }

    @Test
    public void testAnteUp() throws Exception {
        GamePlayer gp = new GamePlayer();
        double score = gp.getScore();
        assertTrue("AnteUp should not fail if user has score",gp.anteUp());
        assertEquals("Score did not remove bet", score - gp.getActiveBet(), gp.getScore());
        gp = new GamePlayer(3);
        assertFalse("Should not pass if player lacks score", gp.anteUp());
        assertEquals("Score should not change if ante fails", 3.0, gp.getScore());
    }

    @Test
    public void testIncreaseBet() throws Exception {
        GamePlayer gp = new GamePlayer();
        gp.increaseBet();
        assertEquals("Bet did not increase as expected", 10, gp.getCurrentBet());
        for(int x = 0; x < 25; x++){
            gp.increaseBet();
        }
        assertEquals("Bet should max out at 100",100,gp.getCurrentBet());

    }

    @Test
    public void testDecreaseBet() throws Exception {
        GamePlayer gp = new GamePlayer();
        for(int x = 0; x < 20; x++){
            gp.increaseBet();
        }
        gp.decreaseBet();
        assertEquals("Bet did not decrease as expected",95,gp.getCurrentBet());
        for(int x = 0; x < 25; x++){
            gp.decreaseBet();
        }
        assertEquals("Bet should min at 5",5,gp.getCurrentBet());
    }

    @Test
    public void testCommitBet() throws Exception {
        GamePlayer gp = new GamePlayer();
        double score = gp.getScore();
        assertTrue("Commit bet should pass if user has score", gp.commitBet());
        assertEquals("Score did not remove bet", score - gp.getActiveBet(), gp.getScore());
        gp = new GamePlayer(10);
        gp.increaseBet();
        gp.increaseBet();
        assertFalse("Commit should fail if user doesn't have the score", gp.commitBet());
        assertEquals("Score should not change if commit fails", 10.0, gp.getScore());
    }

    @Test
    public void testWin() throws Exception {
        GamePlayer gp = new GamePlayer();
        double score = gp.getScore();
        gp.increaseBet();
        gp.increaseBet();
        gp.commitBet();
        double bet = gp.getActiveBet();
        double win = bet*2;
        gp.win();
        assertEquals("Expected 30, got " + win, score + win - bet, gp.getScore());
        assertEquals("Active bet did not clear", 0, gp.getActiveBet());
    }

    @Test
    public void testWinBig() throws Exception {
        GamePlayer gp = new GamePlayer();
        double score = gp.getScore();
        gp.increaseBet();
        gp.increaseBet();
        gp.commitBet();
        double bet = gp.getActiveBet();
        double win = bet*2.5;
        gp.winBig();
        assertEquals("Expected 37.5, got " + win, score+win-bet, gp.getScore());
        assertEquals("Active bet did not clear", 0, gp.getActiveBet());
    }

    @Test
    public void testRefund() throws Exception {
        GamePlayer gp = new GamePlayer();
        gp.increaseBet();
        gp.increaseBet();
        gp.commitBet();
        gp.refund();
        assertEquals("Expected 100, got " + gp.getScore(), 100.0, gp.getScore());
        assertEquals("Active bet did not clear", 0, gp.getActiveBet());
    }

    @Test
    public void testEndRound() throws Exception {
        GamePlayer gp = new GamePlayer();
        ArrayList<Card> test = new ArrayList<Card>();
        for(int x = 7; x < 16; x++){
            Card c = new Card(x);
            test.add(c);
            gp.hit(c);
        }
        ArrayList<Card> trash = gp.endRound();
        ArrayList<Card> out = gp.showHand();
        boolean result = false;
        if(trash.size() == 9 && out.size() == 0)
            result = true;
        assertTrue("Dealer hand did not clear properly",result);
        assertEquals("Active bet did not clear", 0, gp.getActiveBet());
    }
}