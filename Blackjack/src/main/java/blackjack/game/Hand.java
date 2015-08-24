package blackjack.game;
/**
 * @author Alex Euzent
 * @since 1.8
 * Maintains a hand of Card objects
 */
import java.util.*;

class Hand{

	private ArrayList<Card> hand;
	private int handSum;

	/**
	 * Constructor for Hand
	 */
	public Hand() {
		hand = new ArrayList<Card>();
		handSum = 0;
	}
	

	/**
	 * Adds card to hand
	 * @param card Card to be added to the hand
	 */
	public void addCard(Card card){

		hand.add(card);
		runHandSum();
	}
	
	
	/**
	 * Calculates the hand sum of current cards
	 */
	private void runHandSum(){
		handSum=0;
		int aces = 0;
		for(Card card : hand){
			switch(card.getRank()){
				case Card.ACE: //Ace
					aces++;
					break;
					
				case Card.TWO: //Two
					handSum += 2;
					break;
			
				case Card.THREE: //Three
					handSum += 3;
					break;
					
				case Card.FOUR: //Four
					handSum += 4;
					break;
					
				case Card.FIVE: //Five
					handSum += 5;
					break;
					
				case Card.SIX: //Six
					handSum += 6;
					break;
					
				case Card.SEVEN: //Seven
					handSum += 7;
					break;
			
				case Card.EIGHT: //Eight
					handSum += 8;
					break;
					
				case Card.NINE: //Nine
					handSum += 9;
					break;
					
				case Card.TEN: //Ten
				case Card.JACK: //Jack
				case Card.QUEEN: //Queen
				case Card.KING: //King
					handSum += 10;
					break;			
			}
			
		}
		if(aces > 0){
			for(int x = 0; x < aces; x++){
				if(handSum<=10){
					handSum += 11;
				} else {
					handSum += 1;
				}
			}
		}
	}
	
	
	/**
	 * Returns a copy of cards in hand
	 * @return Copy of cards in hand
	 */
	public ArrayList<Card> showCards(){
		return new ArrayList<Card>(hand);
	}
	
	
	/**
	 * Returns hand, empties hand and resets hand sum
	 * @return Used to return cards to game deck
	 */
	public ArrayList<Card> clearHand(){
		ArrayList<Card> temp = new ArrayList<Card>(hand); 
		hand.clear();
		handSum = 0;
		return temp;
	}
	
	
	/**
	 * Accessor for hand sum
	 * @return Current score value of hand
	 */
	public int getHandSum(){
		return handSum;
	}
	
	
}
