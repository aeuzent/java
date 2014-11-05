/**
 * @filename GameEngine.java
 * @author Alex Euzent
 * @date 7/5/2014
 * @purpose Blackjack Game
 */

import java.util.*;

public class GameEngine {

	private GameDeck deck;
	private boolean cardsDealt;
	private GamePlayer player;
	private Dealer dealer;
	private Round round;
	
	public final int QUIT = 0;
	public final int UPBET = 1;
	public final int DOWNBET = 2;
	public final int GOBET = 3;
	public final int HIT = 4;
	public final int STAY = 5;
	
	
	/**
	 * Constructor for GameEngine
	 */
	public GameEngine() {
		deck = new GameDeck();
		player = new GamePlayer();
		dealer = new Dealer();
		round = new Round(player, dealer, deck);
		cardsDealt = false;
	}

	
	/**
	 * Constructor for GameEngine
	 * that loads a players 
	 * previous score
	 * @param playScore
	 */
	public GameEngine(double playScore) {
		deck = new GameDeck();
		player = new GamePlayer(playScore);
		dealer = new Dealer();
		round = new Round(player, dealer, deck);
		cardsDealt = false;
	}
	
	
	/**
	 * Starts a round of play
	 * and deals cards. Returns 
	 * true if round started successfully
	 * @return boolean
	 */
	public boolean startRound(){
		boolean go = round.startRound();
		if(go){
			cardsDealt = true;
		}
		return go;
	}
	
	
	/**
	 * Ends a round of play
	 */
	public void endRound(){
		round.endRound();
		cardsDealt = false;
	}
	
	
	/**
	 * True if a round is in progress
	 * @return
	 */
	public boolean isRoundActive(){
		return round.isGameActive();
	}
	
	
	/**
	 * True if player won last game.
	 * Should only be called if 
	 * isRoundActive is false
	 * @return
	 */
	public boolean didPlayerWin(){
		return round.didPlayerWin();
	}
	
	
	/**
	 * True if last game was a tie.
	 * Should only be called if 
	 * isRoundActive is false
	 * @return
	 */
	public boolean didGameTie(){
		return round.gameTied();
	}
	
	
	/**
	 * Processes a player's input once 
	 * a round is in progress. The 
	 * boolean return is only for the 
	 * commit bet option and will be 
	 * false if the player lacks the score
	 * to make a bet. All other options should
	 * always be true.
	 * @param tag
	 * @return boolean
	 */
	public boolean playersTurn(int tag){
		boolean done = true;
		if(cardsDealt){
			switch(tag){
				case 0: //quit
						endRound();
						break;
					
				case 1:	//increase bet
						player.increaseBet();
						break;
					
				case 2: //decrease bet
						player.decreaseBet();
						break;
				
				case 3: //commit bet
						done = round.playerBet();
						break;
				
				case 4: //hit
						round.playerHit();
						break;
				
				case 5: //stay
						round.playerStay();
						break;
						
				

			}
		}
		return done;
	}
	
	

	/**
	 * Allows a player to change their selected
	 * Bet before a round begins
	 * @param tag
	 */
	public void preBet(int tag){
		switch(tag){
			case 1:	//increase bet
				player.increaseBet();
				break;
					
			case 2: //decrease bet
				player.decreaseBet();
				break;
			
		}
	}
	
	
	/**
	 * Returns a copy of the players hand
	 * for display
	 * @return
	 */
	public ArrayList<Card> getPlayerHand(){
		return player.showHand();
	}
	
	
	/**
	 * Returns a copy of the dealers hand
	 * for display
	 * @return
	 */
	public ArrayList<Card> getDealerHand(){
		return dealer.showHand();
	}
	
	
	/**
	 * Returns the amount the player has
	 * currently bet for display
	 * @return
	 */
	public int getPlayerBet(){
		return player.getActiveBet();
	}
	
	
	/**
	 * Returns the currently selected bet amount
	 * for display
	 * @return
	 */
	public int getSelectedBet(){
		return player.getCurrentBet();
	}
	
	
	/**
	 * Returns the players current score
	 * for display
	 * @return
	 */
	public double getPlayerScore(){
		return player.getScore();
	}
	

	
}
