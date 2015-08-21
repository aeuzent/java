package blackjack.game;
//This source is meant to be a guide on how to use the
//GameEngine class to run a blackjack game. The GameEngine
//class is meant to be a board thats state can be read 
//at any time and limits user input to a few carefully
//designed methods. You can compile and run this to play
//a text version of the game which is fully functional.
import java.util.*;
import java.io.*;

public class Main {

	
	public Main(){
		//ignore this; fixes eclipse bug
	}
	
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    //The game engine source is your access point to the whole game
    //To use it to restore a previous game, just pass the old score
    //to the constructor.
    static GameEngine game = new GameEngine();

    
	public static void main(String[] args) {
		boolean done = false;
		while(!done){
			System.out.println("----------------------------------------------------");
			System.out.println("NEW GAME");
			System.out.println("----------------------------------------------------");

			//Your temporary bet value is stored inside of the game
			//and can be accessed using getSelectedBet.
			System.out.println("\nSelected Bet is " + game.getSelectedBet());
			System.out.println("----------------------------------------------------");

			int x = preDealMenu();

			switch(x){
				case 0: done = true;
						break;
						
						//Changing a bet before a game starts 
						//should be done with the preBet method
						//The value selected will be committed
						//once a round starts
				case 1: game.preBet(game.UPBET);
						break;

				case 2: game.preBet(game.DOWNBET);
						break;
						
				case 3: runGame();
						break;
						
			}
		}
		
	}

	//This method showcases some of the changes I made about betting
	//Now each round can fail when you try to start it if the player
	//lacks the score to ante up
	public static void runGame(){
		if(game.startRound()){
			runRound();
		} else {
			System.out.println("----------------------------------------------------");
			System.out.println("Game could not be started. Player lacks score to play.");
		}
		
	}
	
	public static void runRound(){
		
		boolean done = false;
		while(!done){
			displayGame();
			int x = inGameMenu();
			switch(x){
			case 0: done = true;
					//End round is called automatically when a win occurs
					//but you must call it explicitly if you want to end
					//a round early
					game.endRound();
					break;
					
					//The playersTurn method handles all
					//ingame interaction with the player.
					//I've written a series of constant ints
					//that will make calling this function very
					//easy. 
			case 1: game.playersTurn(game.UPBET);
					break;

			case 2: game.playersTurn(game.DOWNBET);
					break;
					
			case 3: //The whole playersTurn method is now a boolean but commiting
					//a bet is the only place it can fail. This will only happen if 
					//the player lacks the score to make a bet. Otherwise it will always
					//return true
					if(!game.playersTurn(game.GOBET)){
						System.out.println("Player lacks score to make bet");
					};
					break;
					
			case 4: game.playersTurn(game.HIT);
					break;
					
			case 5: game.playersTurn(game.STAY);
					break;
					
			default: //this should handle invalid input
					break;
			}
			
			//isRoundActive is your way to see if a game
			//is in progress
			if(!game.isRoundActive()){
				//didPlayerWin is true only if the player won
				//the last round. But it should not be called unless 
				//isRoundActive is false
				winScreen(game.didPlayerWin());
				game.endRound();
				done = true;
			}
			
		}
		
		
		
	}
	
	
	
	public static int preDealMenu(){
		int i = 0;
		System.out.println("\nPre-Game Menu");
		System.out.println("0 - Quit");
		System.out.println("1 - Increase Bet");
		System.out.println("2 - Decrease Bet");
		System.out.println("3 - Deal Cards");
		System.out.print("Make Selection: ");
		try{
			i = Integer.parseInt(br.readLine());
			
		}catch(NumberFormatException nfe){
			System.err.println("Invalid Format!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	public static int inGameMenu(){
		int i = 0;
		System.out.println("\nIn-Game Menu");
		System.out.println("0 - Quit");
		System.out.println("1 - Increase Bet");
		System.out.println("2 - Decrease Bet");
		System.out.println("3 - Commit Bet");
		System.out.println("4 - Hit");
		System.out.println("5 - Stay");
		System.out.print("Make Selection: ");
		try{
			i = Integer.parseInt(br.readLine());
			
		}catch(NumberFormatException nfe){
			System.err.println("Invalid Format!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	public static void displayGame(){
		//You can get a copy of the cards currently in play by using the below commands
		ArrayList<Card> playerHand = new ArrayList<Card>(game.getPlayerHand());
		ArrayList<Card> dealerHand = new ArrayList<Card>(game.getDealerHand());
		
		//Methods to retrieve the players bets and score are detailed below.
		//playerBet is the amount currently being bet on this round 
		int playerBet = game.getPlayerBet();
		int selectedBet = game.getSelectedBet();
		double playerScore = game.getPlayerScore();
		
		System.out.println("----------------------------------------------------");
		System.out.println("Dealer's Cards: ");
		
		//Be sure to hide the first card in the dealers hand
		//getDealerHand will give you all the cards
		System.out.println("XXXXXXXXXXXXXX");
		for(int x = 1; x < dealerHand.size(); x++){
			System.out.println(dealerHand.get(x));
		}
		System.out.println("\nPlayer's Cards: ");
		for(Card card : playerHand){
			System.out.println(card);
		}
		System.out.println("----------------------------------------------------");

		System.out.println("\nAmmount currently bet: " + playerBet);
		System.out.println("Selected Bet: " + selectedBet);
		System.out.println("Player score is " + playerScore);
		System.out.println("----------------------------------------------------");

	}
	
	public static void winScreen(boolean playerWin){
		ArrayList<Card> playerHand = new ArrayList<Card>(game.getPlayerHand());
		ArrayList<Card> dealerHand = new ArrayList<Card>(game.getDealerHand());
		double playerScore = game.getPlayerScore();
		
		System.out.println("----------------------------------------------------");
		System.out.println("GAME OVER");
		//didPlayerTie is true only if the last round 
		//ended in a tie. It should be called before the
		//didPlayerWin method as it's value will be incorrect
		//if the round was a tie. But neither should be called 
		//unless isRoundActive is false
		if(game.didGameTie()){
			System.out.println("----------------------------------------------------");
			System.out.println("Game Tied");
		}else if(playerWin){
			System.out.println("----------------------------------------------------");
			System.out.println("Player Wins!");
		} else {
			System.out.println("----------------------------------------------------");
			System.out.println("Player Loses");

		}
		System.out.println("Score = " + playerScore);
		System.out.println("----------------------------------------------------");
		System.out.println("Dealer's Cards: ");
		for(int x = 0; x < dealerHand.size(); x++){
			System.out.println(dealerHand.get(x));
		}
		System.out.println("\nPlayer's Cards: ");
		for(Card card : playerHand){
			System.out.println(card);
		}
		System.out.println("----------------------------------------------------");
	}
	
	
}
