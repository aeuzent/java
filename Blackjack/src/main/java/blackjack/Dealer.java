package blackjack;

/**
 * @filename Dealer.java
 * @author Alex Euzent
 * @date 7/15/2014
 * @purpose Blackjack Game
 */
import java.util.*;

public class Dealer{

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
	 * @return
	 */
	public ArrayList<Card> showHand(){
		return hand.showCards();
	}
	
	
	/**
	 * Returns current hand sum
	 * @return
	 */
	public int currHandSum(){
		return hand.getHandSum();
	}

	
	/**
	 * Adds a new card to the hand
	 * @param card
	 */
	public void hit(Card card){
		hand.addCard(card);
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
