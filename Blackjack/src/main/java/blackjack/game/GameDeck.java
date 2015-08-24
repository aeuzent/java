package blackjack.game;
/**
 * @author Alex Euzent
 * @since 1.8
 * Maintains a deck of Card objects
 */
import java.util.*;


class GameDeck{
	
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
	 * @return Card taken from the top of the deck
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
	 * @param card Card to be returned to the deck
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
