package blackjack.game;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex Euzent on 8/20/2015.
 */
public class RoundTest extends TestCase {
    private GameDeck gd;
    private Dealer dlr;
    private GamePlayer gp;

    @Before
    public void setUp(){
        gd = new GameDeck();
        dlr = new Dealer();
        gp = new GamePlayer();
    }

    @Test
    public void testStartRound() throws Exception {
        Round rnd = new Round(gp,dlr,gd);
        assertTrue("Round did not start as expected",rnd.startRound());
        gp = new GamePlayer(-5);
        rnd = new Round(gp,dlr,gd);
        assertFalse("Round should of failed to start due to player score", rnd.startRound());
    }

    @Test
    public void testEndRound() throws Exception {
        Round rnd = new Round(gp,dlr,gd);
        rnd.endRound();
        assertFalse("Round did not end as expected",rnd.isGameActive());
    }

    @Test
    public void testIsGameActive() throws Exception {
        Round rnd = new Round(gp,dlr,gd);
        assertTrue("Round should start on default",rnd.isGameActive());
        rnd.startRound();
        assertTrue("Round should be started", rnd.isGameActive());
        rnd.endRound();
        assertFalse("Round did not end as expected",rnd.isGameActive());
    }

    @Test
    public void testDidPlayerWin() throws Exception {
        //Player hand == 21
        Card c = new Card(Card.ACE,Card.CLUBS);
        Card d = new Card(Card.KING, Card.HEARTS);
        gp.hit(c);
        gp.hit(d);

        //Dealer hand == 13
        Card e =  new Card(Card.THREE,Card.DIAMONDS);
        dlr.hit(d);
        dlr.hit(e);

        Round rnd = new Round(gp,dlr,gd);
        rnd.pickWinner();
        assertTrue("Player should have won", rnd.didPlayerWin());
    }

    @Test
    public void testPlayerHit() throws Exception {

    }

    @Test
    public void testPlayerStay() throws Exception {

    }

    @Test
    public void testPlayerBet() throws Exception {

    }

    @Test
    public void testPickWinner() throws Exception {

    }

    @Test
    public void testGameTied() throws Exception {

    }
}