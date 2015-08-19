package blackjack;
/**
 * @filename GameDeck.java
 * @author Alex Euzent
 * @date 7/15/2014
 * @purpose Blackjack Game
 */
import java.util.*;


public class GameDeck{
	
	private ArrayDeque<Card> deck;
	private ArrayList<Card> trash;
	
	
	/**
	 * Constructor for GameDeck
	 */
	public GameDeck() {
		deck = new ArrayDeque<Card>();
		trash = new ArrayList<Card>();
		
		//Fill with 1 decks
		for(int y = 0; y < 52; y++){
			Card c = new Card(y);
			trash.add(c);
		
		}
		shuffle();
		
	}
	
	
	/**
	 * Removes  and returns card 
	 * from top of deck. Shuffles cards
	 * if none left. 
	 * @return
	 */
	public Card getCard(){
		if(!deck.isEmpty()){
			return deck.removeFirst();
		} else {
			shuffle();
			return deck.removeFirst();
		}
	}
	
	
	/**
	 * Returns card to the deck
	 * @param card
	 */
	public void returnCard(Card card){
		trash.add(card);
	}
	
	
	/**
	 * Shuffles deck
	 */
	private void shuffle(){
		Collections.shuffle(trash);
		for(Card current : trash){
			deck.addFirst(current);
		}

		trash.clear();
	}
	
	
}
