package blackjack;
/**
 * @filename GamePlayer.java
 * @author Alex Euzent
 * @date 7/15/2014
 * @purpose Blackjack Game
 */
import java.util.*;

public class GamePlayer{

	private transient Hand hand;
	private double score;
	private transient int currBet, activeBet;
	
	
	/**
	 * Constructor for player
	 */
	public GamePlayer() {
		hand = new Hand();
		score = 100;
		currBet = 5;
		activeBet = 0;

	}

	
	/**
	 * Constructor for player that
	 * loads previous score
	 * @param score
	 */
	public GamePlayer(double score) {
		hand = new Hand();
		this.score = score;
		currBet = 5;
		activeBet = 0;

	}

	
	/**
	 * Returns a copy of cards
	 * currently in hand
	 * @return ArrayList<Card>
	 */
	public ArrayList<Card> showHand(){
		return hand.showCards();
	}
	
	
	/**
	 * Returns current hand sum
	 * @return int
	 */
	public int currHandSum(){
		return hand.getHandSum();
	}
	
	
	/**
	 * Accessor for player score
	 * @return double
	 */
	public double getScore(){
		return score;
	}
	
	
	/**
	 * Accessor for currently
	 * selected bet amount
	 * @return int
	 */
	public int getCurrentBet(){
		return currBet;
	}
	
	
	/**
	 * Returns amount that is 
	 * currently being bet
	 * @return int
	 */
	public int getActiveBet(){
		return activeBet;
	}
	
	
	/**
	 * Makes opening bet at the start
	 * of a round. Returns false if 
	 * player lacks score to make bet
	 * @return boolean
	 */
	public boolean anteUp(){
		boolean done = false;
		if(score - currBet >= 0){
			activeBet = currBet;
			score -= activeBet;
			done = true;
		}
		return done;
		
	}
	
	
	/**
	 * Increases selected bet 
	 * by 5 to a max of 100
	 */
	public void increaseBet(){
		if(currBet <= 95){
			currBet += 5;
		} else {
			currBet = 100;
		}
	}
	
	
	/**
	 * Decreases selected bet 
	 * by 5 to a minimum of 5
	 */
	public void decreaseBet(){
		if(currBet >= 10){
			currBet -= 5;
		} else {
			currBet = 5;
		}
	}
	
	
	/**
	 * Commits current selected bet as
	 * an active bet. Returns false if 
	 * player lacks score to make bet
	 * @return boolean
	 */
	public boolean commitBet(){
		boolean done = false;
		int newBet = currBet;
		if(score - newBet >= 0){
			score -= newBet;
			done = true;
		}
		return done;
	}
	
	
	/**
	 * Adds a new card to the hand
	 * @param card
	 */
	public void hit(Card card){
		hand.addCard(card);
	}
	
	
	/**
	 * Increases score for a regular win
	 */
	public void win(){
		score += activeBet*2;
	}
	
	
	/**
	 * Increases score for a 21 win
	 */
	public void winBig(){
		score += activeBet*2.5;
	}
	
	
	/**
	 * Refunds bet, used in a tie
	 */
	public void refund(){
		score += activeBet;
	}
	
	
	/**
	 * Returns all cards to be added back to the deck
	 * and clears hand
	 * @return
	 */
	public ArrayList<Card> endRound(){
		ArrayList<Card> temp = new ArrayList<Card>(hand.clearHand());
		return temp;
	}
	


}
