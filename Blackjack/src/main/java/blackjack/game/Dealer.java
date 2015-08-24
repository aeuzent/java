package blackjack.game;

/**
 * @author Alex Euzent
 * @since 1.8
 * Object to represent the dealer
 * and maintain its hand
 */
import java.util.*;

class Dealer{

	private Hand hand;
	
	
	/**
	 * Constructor for Dealer
	 */
	public Dealer() {
		hand = new Hand();
	}
	
	
	/**
	 * Returns a copy of cards
	 * currently in hand
	 * @return Copy of cards in hand
	 */
	public ArrayList<Card> showHand(){
		return new ArrayList<Card>(hand.showCards());
	}
	
	
	/**
	 * Returns current hand sum
	 * @return Score value of cards in hand
	 */
	public int currHandSum(){
		return hand.getHandSum();
	}

	
	/**
	 * Adds a new card to the hand
	 * @param card Card to be added to hand
	 */
	public void hit(Card card){
		hand.addCard(card);
	}
	
	
	/**
	 * Returns all cards to be added back to the deck
	 * and clears hand
	 * @return Used to return cards to the game deck
	 */
	public ArrayList<Card> endRound(){
		return hand.clearHand();
	}
}
