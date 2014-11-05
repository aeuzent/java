/**
 * @filename Round.java
 * @author Alex Euzent
 * @date 6/20/2014
 * @purpose Blackjack Game
 */
import java.util.ArrayList;

public class Round{

	private GameDeck deck;
	private boolean playerTurn, noWinner, playerWin, tie, firstTurn;
	private int moves, lastMoves, roundCount;
	private GamePlayer player;
	private Dealer dealer;
	
	
	/**
	 * Constructor for Round
	 * @param player
	 * @param dealer
	 * @param deck
	 */
	public Round(GamePlayer player, Dealer dealer, GameDeck deck) {
		this.deck = deck;
		this.player = player;
		this.dealer = dealer;
		noWinner = true;
		playerTurn = true;
		moves = 0;
		lastMoves = 0;
		roundCount = 0;
	}


	/**
	 * Resets object for a new round.
	 * Returns true if round started
	 * successfully. False if player
	 * lacks score to ante up
	 * @return boolean
	 */
	public boolean startRound(){
		boolean ante = player.anteUp();
		if(ante){
			if(roundCount > 0){
				collectTrash();
			}			
			roundCount++;
			firstTurn = true;
			playerTurn = true;
			tie = false;
			lastMoves = moves; 
			moves = 0;
			noWinner = true;
			playerWin = false;
			deal();
		} else {
			noWinner = false;
		}
		return ante;
	}
	
	
	/**
	 * Deals cards and collects opening bet
	 */
	private void deal(){
		for(int x = 0; x < 4; x++){
			if(x/2 == 0){
				//dealers cards
				dealer.hit(deck.getCard());
			} else {
				//player cards
				player.hit(deck.getCard());
			}
		}		
	}
	
	
	/**
	 * Ends current round of play
	 */
	public void endRound(){
		noWinner = false;
	}
	
	
	/**
	 * Collects all cards and returns them to the deck
	 */
	private void collectTrash(){
		ArrayList<Card> trash = new ArrayList<Card>(player.endRound());
		trash.addAll(dealer.endRound());
		
		for(Card current : trash){
			deck.returnCard(current);
		}
	}
	
	
	/**
	 * True if round is in progress
	 * @return
	 */
	public boolean isGameActive(){
		return noWinner;
	}
	
	
	/**
	 * True is player won match
	 * Should only be called if 
	 * isGameActive is false
	 * @return
	 */
	public boolean didPlayerWin(){
		return playerWin;
	}
	
	
	/**
	 * Performs dealers turn
	 */
	private void dealersTurn(){
		
		boolean stay = false;
		if(!playerTurn){
			
			while(!stay && noWinner){
				if(player.currHandSum() > dealer.currHandSum()){
					dealerHit();
				} else {
					if(dealer.currHandSum() < 17){
						dealerHit();
						
					} else if (dealer.currHandSum() >= 17 && dealer.currHandSum() < 21){

						stay = true;
						if(!firstTurn){
							if(lastMoves == 0 && moves == 0){
								pickWinner();
							}
						}
						firstTurn = false;
					}
				}
			}
			playerTurn = true;
			lastMoves = moves; 
			moves = 0;
		}
		
	}
	
	
	/**
	 * Method to allow dealer to hit
	 */
	private void dealerHit(){
		moves++;
		dealer.hit(deck.getCard());
		if(dealer.currHandSum() == 21){
			playerWin = false;
			endRound();
		} else if (dealer.currHandSum() > 21) {
			playerWin = true;
			player.win();
			endRound();
		}
	}
	
	
	/**
	 * Method to allow player to hit
	 */
	public void playerHit(){
		if(playerTurn){
			player.hit(deck.getCard());
			moves++;
			if(player.currHandSum() == 21){
				playerWin = true;
				player.winBig();
				endRound();
			} else if (player.currHandSum() > 21) {
				playerWin = false;
				endRound();
			}
		}
	}
	
	
	/**
	 * Method to allow player to stay
	 * Calls dealersTurn when done
	 */
	public void playerStay(){
		if(playerTurn){
			playerTurn = false;
			if(!firstTurn){
				if(lastMoves == 0 && moves == 0){
					pickWinner();
				} else {
					lastMoves = moves; 
					moves = 0;
					dealersTurn();
				}
			} else {
				dealersTurn();
			}
			firstTurn = false;
		}
	}
	
	
	/**
	 * Allows a player to commit a bet 
	 * if it's their turn. Returns false
	 * if player lacks score to bet.
	 * @return boolean
	 */
	public boolean playerBet(){
		boolean done = false;
		if(playerTurn){
			done = player.commitBet();
		}
		return done;
	}
	
	
	/**
	 * Decides winner if no-one got 21 or
	 * busted. 
	 */
	public void pickWinner(){
		if(player.currHandSum() == dealer.currHandSum()){
			playerWin = false;
			tie = true;
			player.refund();
			endRound();
		} else if(player.currHandSum() > dealer.currHandSum()){
			playerWin = true;
			player.win();
			endRound();
		} else {
			playerWin = false;
			endRound();
		}
	}
	
	
	/**
	 * True if game tied
	 * Should only be called if 
	 * isGameActive is false
	 * @return
	 */
	public boolean gameTied(){
		return tie;
	}
	
}
