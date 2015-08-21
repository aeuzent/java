package blackjack.game;

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

	public static final int HEARTS = 0;
	public static final int DIAMONDS = 1;
	public static final int CLUBS = 2;
	public static final int SPADES = 3;

    public static final int ACE = 0;
	public static final int TWO = 1;
	public static final int THREE = 2;
	public static final int FOUR = 3;
	public static final int FIVE = 4;
	public static final int SIX = 5;
	public static final int SEVEN = 6;
	public static final int EIGHT = 7;
	public static final int NINE = 8;
	public static final int TEN = 9;
	public static final int JACK = 10;
    public static final int QUEEN = 11;
    public static final int KING = 12;

	/**
	 * Constructor for Card
	 * @param in
	 */
	public Card(int in) throws IllegalArgumentException{
        if(in>=0 && in < 52) {
            suit = in / 13;
            rank = in % 13;
            cardName = rankNames[rank] + " of " + suitNames[suit];
        } else {
            throw new IllegalArgumentException("Cannot create card from " + in);
        }
	}

    /**
     * Constructor for Card
     */
    public Card(int rank, int suit) throws IllegalArgumentException{
        if(rank>=0 && rank < 13) {
            if(suit>=0 && suit < 4) {
                this.suit = suit;
                this.rank = rank;
                cardName = rankNames[this.rank] + " of " + suitNames[this.suit];
            } else {
                throw new IllegalArgumentException("Suit " + suit + "is invalid");
            }
        } else {
            throw new IllegalArgumentException("Rank " + rank + "is invalid");
        }
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
