package blackjack;
/**
 * @filename Hand.java
 * @author Alex Euzent
 * @date 7/15/2014
 * @purpose Blackjack Game
 */
import java.util.*;

public class Hand{

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
	 * @param card
	 */
	public void getCard(Card card){

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
				case 0: //Ace
					aces++;
					break;
					
				case 1: //Two
					handSum += 2;
					break;
			
				case 2: //Three
					handSum += 3;
					break;
					
				case 3: //Four
					handSum += 4;
					break;
					
				case 4: //Five
					handSum += 5;
					break;
					
				case 5: //Six
					handSum += 6;
					break;
					
				case 6: //Seven
					handSum += 7;
					break;
			
				case 7: //Eight
					handSum += 8;
					break;
					
				case 8: //Nine
					handSum += 9;
					break;
					
				case 9: //Ten
				case 10: //Jack
				case 11: //Queen
				case 12: //King
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
	 * @return
	 */
	public ArrayList<Card> showCards(){
		ArrayList<Card> out = new ArrayList<Card>(hand);
		
		return out;
	}
	
	
	/**
	 * Returns hand, empties hand and resets hand sum
	 * @return
	 */
	public ArrayList<Card> clearHand(){
		ArrayList<Card> temp = new ArrayList<Card>(hand); 
		hand.clear();
		handSum = 0;
		return temp;
	}
	
	
	/**
	 * Accessor for hand sum
	 * @return
	 */
	public int getHandSum(){
		return handSum;
	}
	
	
}
