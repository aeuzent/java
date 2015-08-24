package blackjack.game;
/**
 * @author Alex Euzent
 * @since 1.8
 * Runs the actual blackjack game
 * Only object that needs to be created
 * to run the game
 */

import java.util.*;

class GameEngine {

	private GameDeck deck;
	private boolean cardsDealt;
	private GamePlayer player;
	private Dealer dealer;
	private Round round;
	
	public static final int QUIT = 0;
	public static final int UPBET = 1;
	public static final int DOWNBET = 2;
	public static final int GOBET = 3;
	public static final int HIT = 4;
	public static final int STAY = 5;
	
	
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
	 * @param playScore Value to be set as players current score
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
	 * @return True if round starts correctly
	 */
	public boolean startRound(){
		if(round.startRound()){
			cardsDealt = true;
		}
		return cardsDealt;
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
	 * @return True if round is in play
	 */
	public boolean isRoundActive(){
		return round.isGameActive();
	}
	
	
	/**
	 * True if player won last game.
	 * Should only be called if 
	 * isRoundActive is false
	 * @return True if player won game, may give bad value if game is tied
	 */
	public boolean didPlayerWin(){
		return round.didPlayerWin();
	}
	
	
	/**
	 * True if last game was a tie.
	 * Should only be called if 
	 * isRoundActive is false
	 * @return True if game is tied
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
	 * @param tag User's choice of their next action
	 * @return True in all cases except when player
     *          lacks score to play
	 */
	public boolean playersTurn(int tag){
		boolean done = true;
        if(cardsDealt){
			switch(tag){
				case GameEngine.QUIT: //quit
						endRound();
						break;
					
				case GameEngine.UPBET:	//increase bet
						player.increaseBet();
						break;
					
				case GameEngine.DOWNBET: //decrease bet
						player.decreaseBet();
						break;
				
				case GameEngine.GOBET: //commit bet
						done = round.playerBet();
						break;
				
				case GameEngine.HIT: //hit
						round.playerHit();
						break;
				
				case GameEngine.STAY: //stay
						round.playerStay();
						break;
						
				

			}
		} else {
            done = false;
        }
		return done;
	}
	
	

	/**
	 * Allows a player to change their selected
	 * Bet before a round begins
	 * @param tag Player's choice of next action
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
	 * @return Copy of players hand
	 */
	public ArrayList<Card> getPlayerHand(){
		return round.getPlayersHand();
	}
	
	
	/**
	 * Returns a copy of the dealers hand
	 * for display
	 * @return Copy of Dealers hand
	 */
	public ArrayList<Card> getDealerHand(){
		return round.getDealersHand();
	}
	
	
	/**
	 * Returns the amount the player has
	 * currently bet for display
	 * @return Players current committed bet
	 */
	public int getPlayerBet(){
		return player.getActiveBet();
	}
	
	
	/**
	 * Returns the currently selected bet amount
	 * for display
	 * @return Players current selected bet
	 */
	public int getSelectedBet(){
		return player.getCurrentBet();
	}
	
	
	/**
	 * Returns the players current score
	 * for display
	 * @return Players current score
	 */
	public double getPlayerScore(){
		return player.getScore();
	}
	

	
}
