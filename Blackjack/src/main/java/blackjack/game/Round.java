package blackjack.game;
/**
 * @author Alex Euzent
 * @since 1.8
 * Runs and maintains a round of Blackjack
 * This is the source where the actual game
 * takes place
 */
import java.util.ArrayList;

class Round{

	private GameDeck deck;
	private boolean playerTurn, roundActive, playerWin, tie, firstTurn;
	private int moves, lastMoves;
	private GamePlayer player;
	private Dealer dealer;
	private ArrayList<Card> lastPlayerHand, lastDealerHand;
	
	/**
	 * Constructor for Round
	 * @param player Object to represent the user
	 * @param dealer Object to represent the dealer
	 * @param deck Deck of Card objects, 52 on default
	 */
	public Round(GamePlayer player, Dealer dealer, GameDeck deck) {
		this.deck = deck;
		this.player = player;
		this.dealer = dealer;
		roundActive = true;
		playerTurn = true;
		moves = 0;
		lastMoves = 0;
	}


	/**
	 * Resets object for a new round.
	 * Returns true if round started
	 * successfully. False if player
	 * lacks score to ante up
	 * @return True if the round starts
     * correctly, only false if user
     * lacks score to play
	 */
	public boolean startRound(){
		boolean ante = player.anteUp();
		if(ante){
			firstTurn = true;
			playerTurn = true;
			tie = false;
			lastMoves = moves;
			moves = 0;
            roundActive = true;
			playerWin = false;
			deal();
		} else {
			roundActive = false;
		}
		return ante;
	}
	
	
	/**
	 * Deals cards, 2 each
	 */
	private void deal(){
		for(int x = 0; x < 4; x++){
			if(x/2 == 0){
				//dealers cards
				dealer.hit(deck.getCard());
			} else {
				//player cards
				player.hit(deck.getCard());
			}
		}		
	}
	
	
	/**
	 * Ends current round of play
	 */
	public void endRound(){
		roundActive = false;
        collectTrash();
	}
	
	
	/**
	 * Collects all cards and returns them to the deck.
     * Also makes a backup of previous hand for display
	 */
	private void collectTrash(){
        lastDealerHand = dealer.showHand();
        lastPlayerHand = player.showHand();
		ArrayList<Card> trash = new ArrayList<Card>(player.endRound());
		trash.addAll(dealer.endRound());
		
		for(Card current : trash){
			deck.returnCard(current);
		}
	}
	
	
	/**
	 * True if round is in progress
	 * @return True if round is in progress
	 */
	public boolean isGameActive(){
		return roundActive;
	}
	
	
	/**
	 * True is player won match
	 * Should only be called if 
	 * isGameActive is false
	 * @return True if player won game, may give
     * incorrect value in the event of a tie
	 */
	public boolean didPlayerWin(){
		return playerWin;
	}
	
	
	/**
	 * Performs dealers turn
	 */
	private void dealersTurn(){
		boolean stay = false;
		if(!playerTurn){
			while(!stay && roundActive){
				if(player.currHandSum() > dealer.currHandSum()){
					dealerHit();
				} else {
					if(dealer.currHandSum() < 17){
						dealerHit();
					} else if (dealer.currHandSum() >= 17 && dealer.currHandSum() < 21){
						stay = true;

                        if(!firstTurn){
							if(lastMoves == 0 && moves == 0){
								pickWinner();
							}
						}
						firstTurn = false;
					}
				}
			}
			playerTurn = true;
			lastMoves = moves; 
			moves = 0;
		}
		
	}
	
	
	/**
	 * Method to allow dealer to hit
	 */
	private void dealerHit(){
		moves++;
		dealer.hit(deck.getCard());
		if(dealer.currHandSum() == 21){
			playerWin = false;
			endRound();
		} else if (dealer.currHandSum() > 21) {
			playerWin = true;
			player.win();
			endRound();
		}
	}
	
	
	/**
	 * Method to allow player to hit
	 */
	public void playerHit(){
		if(playerTurn){
			player.hit(deck.getCard());
			moves++;
			if(player.currHandSum() == 21){
				playerWin = true;
				player.winBig();
				endRound();
			} else if (player.currHandSum() > 21) {
				playerWin = false;
				endRound();
			}
		}
	}
	
	
	/**
	 * Method to allow player to stay
	 * Calls dealersTurn when done
	 */
	public void playerStay(){
		if(playerTurn){
			playerTurn = false;
			if(!firstTurn){
				if(lastMoves == 0 && moves == 0){
					pickWinner();
				} else {
					lastMoves = moves; 
					moves = 0;
					dealersTurn();
				}
			} else {
				dealersTurn();
			}
			firstTurn = false;
		}
	}
	
	
	/**
	 * Allows a player to commit a bet 
	 * if it's their turn. Returns false
	 * if player lacks score to bet.
	 * @return True if player has the score to play
	 */
	public boolean playerBet(){
		boolean done = false;
		if(playerTurn){
			done = player.commitBet();
		}
		return done;
	}
	
	
	/**
	 * Decides winner if no-one got 21 or
	 * busted. 
	 */
	public void pickWinner(){
        if(player.currHandSum() > 21){
            playerWin = false;
            endRound();
        } else if (dealer.currHandSum() > 21){
            playerWin = true;
            player.win();
            endRound();
        } else if(player.currHandSum() == dealer.currHandSum()){
			playerWin = false;
			tie = true;
			player.refund();
			endRound();
		} else if(player.currHandSum() > dealer.currHandSum()){
			playerWin = true;
			player.win();
			endRound();
		} else {
			playerWin = false;
			endRound();
		}
	}
	
	
	/**
	 * True if game tied
	 * Should only be called if 
	 * isGameActive is false
	 * @return True if game is tied
	 */
	public boolean gameTied(){
		return tie;
	}

    public ArrayList<Card> getPlayersHand(){
        if(roundActive){
            return player.showHand();
        } else {
            return lastPlayerHand;
        }
    }

    public ArrayList<Card> getDealersHand(){
        if(roundActive){
            return dealer.showHand();
        } else {
            return lastDealerHand;
        }
    }
	
}
