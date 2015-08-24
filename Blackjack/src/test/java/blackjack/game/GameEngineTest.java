package blackjack.game;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex Euzent on 8/20/2015.
 */
public class GameEngineTest extends TestCase {

    @Test
    public void testStartRound() throws Exception {
        GameEngine ge = new GameEngine();
        assertTrue("Game should have started", ge.startRound());

        ge = new GameEngine(3);
        assertFalse("Should fail if player lacks score to play", ge.startRound());
    }

    @Test
    public void testEndRound() throws Exception {
        GameEngine ge = new GameEngine();
        ge.endRound();
        assertFalse("Round should have ended", ge.isRoundActive());
    }

    @Test
    public void testIsRoundActive() throws Exception {
        GameEngine ge = new GameEngine();
        ge.startRound();
        assertTrue("Round should have started", ge.isRoundActive());
    }

    @Test
    public void testPlayersTurn() throws Exception {
        GameEngine ge = new GameEngine();
        ge.startRound();
        assertTrue("Quit should return true", ge.playersTurn(GameEngine.QUIT));
        ge.startRound();
        assertTrue("UpBet should return true", ge.playersTurn(GameEngine.UPBET));
        assertTrue("DownBet should return true", ge.playersTurn(GameEngine.DOWNBET));
        assertTrue("GoBet should return true unless player lacks score", ge.playersTurn(GameEngine.GOBET));
        assertTrue("Hit should return true", ge.playersTurn(GameEngine.HIT));
        assertTrue("Stay should return true", ge.playersTurn(GameEngine.STAY));
        ge = new GameEngine(3);
        ge.startRound();
        assertFalse("GoBet should fail if user lacks score to play", ge.playersTurn(GameEngine.GOBET));

    }

    @Test
    public void testPreBet() throws Exception {
        GameEngine ge = new GameEngine();
        int x = ge.getSelectedBet();
        ge.preBet(GameEngine.UPBET);
        assertTrue("Bet should have increased", ge.getSelectedBet() > x);
        x = ge.getSelectedBet();
        ge.preBet(GameEngine.DOWNBET);
        assertTrue("Bet should have decreased", ge.getSelectedBet() < x);
    }

    @Test
    public void testGetPlayerBet() throws Exception {
        GameEngine ge = new GameEngine();
        ge.startRound();
        ge.playersTurn(GameEngine.GOBET);
        assertEquals("Default bet should be 5", 5, ge.getPlayerBet());
        ge = new GameEngine();
        ge.startRound();
        ge.playersTurn(GameEngine.UPBET);
        ge.playersTurn(GameEngine.UPBET);
        ge.playersTurn(GameEngine.GOBET);
        assertEquals("Bet should be 15", 15, ge.getPlayerBet());
    }

    @Test
    public void testGetSelectedBet() throws Exception {
        GameEngine ge = new GameEngine();
        assertEquals("Default bet should be 5",5,ge.getSelectedBet());
        ge.preBet(GameEngine.UPBET);
        ge.preBet(GameEngine.UPBET);
        ge.preBet(GameEngine.UPBET);
        assertEquals("Should be turned up to 20", 20, ge.getSelectedBet());
        ge.preBet(GameEngine.DOWNBET);
        ge.preBet(GameEngine.DOWNBET);
        assertEquals("Should be turned down to 10", 10, ge.getSelectedBet());
    }

    @Test
    public void testGetPlayerScore() throws Exception {
        GameEngine ge = new GameEngine();
        assertEquals("Default score should be 100", 100.0, ge.getPlayerScore());
        ge = new GameEngine(75);
        assertEquals("Expected a score of 75", 75.0, ge.getPlayerScore());
    }
}