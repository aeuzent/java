package blackjack;

/**
 * @filename Card.java
 * @author Alex Euzent
 * @date 7/17/2014
 * @purpose Blackjack Game
 */


public class Card{

	private int rank, suit;
	private String cardName;
	private static final String[] rankNames = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	private static final String[] suitNames = {"Hearts", "Diamonds", "Clubs", "Spades"};
	
	
	/**
	 * Constructor for Card
	 * @param in
	 */
	public Card(int in) {
		suit = in / 13;
		rank = in % 13;

		cardName = rankNames[rank] + " of " + suitNames[suit];
	}
	
	
	/**
	 * Accessor for card rank
	 * @return
	 */
	public int getRank(){
		return rank;
	}
	
	
	/**
	 * Accessor for card suit
	 * @return
	 */
	public int getSuit(){
		return suit;
	}
	
	
	/**
	 * Returns card formatted as a string
	 */
	@Override
	public String toString(){
		return cardName;
	}
		
}
