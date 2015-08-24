package blackjack.game;
/**
 * @author Alex Euzent
 * @since 1.8
 * Object represents the user and
 * maintains all values and cards
 * related to play.
 */
import java.util.*;

class GamePlayer{

	private Hand hand;
	private double score;
	private int currBet, activeBet;
	
	
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
	 * @param score Sets player score to
     *              passed value
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
	 * @return ArrayList<Card> Card in users hand
	 */
	public ArrayList<Card> showHand(){
		return new ArrayList<Card>(hand.showCards());
	}
	
	
	/**
	 * Returns current hand sum
	 * @return int Score value of cards in hand
	 */
	public int currHandSum(){
		return hand.getHandSum();
	}
	
	
	/**
	 * Accessor for player score
	 * @return double Current player score
	 */
	public double getScore(){
		return score;
	}
	
	
	/**
	 * Accessor for currently
	 * selected bet amount
	 * @return int Current selected bet value
	 */
	public int getCurrentBet(){
		return currBet;
	}
	
	
	/**
	 * Returns amount that is 
	 * currently being bet
	 * @return int Current committed bet value
	 */
	public int getActiveBet(){
		return activeBet;
	}
	
	
	/**
	 * Makes opening bet at the start
	 * of a round. Returns false if 
	 * player lacks score to make bet
	 * @return boolean True if player has score to play
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
	 * @return boolean True if player has score
     * to make selected bet
	 */
	public boolean commitBet(){
		boolean done = false;
		if(score - currBet >= 0){
			activeBet = currBet;
            score = score - activeBet;
			done = true;
		}
		return done;
	}
	
	
	/**
	 * Adds a new card to the hand
	 * @param card Card to be added to the hand
	 */
	public void hit(Card card){
		hand.addCard(card);
	}
	
	
	/**
	 * Increases score for a regular win
	 */
	public void win(){
		score = score + activeBet*2;
        activeBet = 0;
	}
	
	
	/**
	 * Increases score for a 21 win
	 */
	public void winBig(){
		score += activeBet*2.5;
        activeBet = 0;
	}
	
	
	/**
	 * Refunds bet, used in a tie
	 */
	public void refund(){
		score += activeBet;
        activeBet = 0;
	}
	
	
	/**
	 * Returns all cards to be added back to the deck
	 * and clears hand
	 * @return Used for returning current cards to
     * game deck
    */
	public ArrayList<Card> endRound(){
		ArrayList<Card> temp = new ArrayList<Card>(hand.clearHand());
        activeBet = 0;
		return temp;
	}
	


}
