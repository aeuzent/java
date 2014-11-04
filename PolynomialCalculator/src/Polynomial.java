import java.util.ArrayList;

/**
 * @author Alex Euzent
 * Polynomial class is meant to represent a polynomial
 * equation. It contains an ArrayList of Term objects and 
 * maintains it's name as well as the highest and lowest 
 * exponential value. 
 */
public class Polynomial {

	String name;
	ArrayList<Term> terms = new ArrayList<Term>();
	int maxPower;
	int lowPower;
	
	/**
	 * Constructor for Polynomial assigns a name and
	 * passes a string of scalars and exponents to 
	 * be processed into Term objects.
	 * @param newName
	 * @param newTerms
	 */
	public Polynomial(String newName, String newTerms) {
		name = newName;
		processTerms(newTerms);
	}
	
	/**
	 * Receives a string containing scalars and exponents
	 * and processes them into Term objects that are added 
	 * to the ArrayList of other Terms. Method also finds and 
	 * assigns the lowest and highest exponential power. 
	 * @param newTerms
	 */
	private void processTerms(String newTerms) {
		String[] tempTerms = newTerms.split("\\s+");
		int tempScalar = 0;
		int tempPower = 0;
		
		for( int i = 0; i < tempTerms.length; i += 2) {
			tempPower = Integer.parseInt(tempTerms[i]);
			tempScalar = Integer.parseInt(tempTerms[i+1]);
			
			if (tempPower > maxPower)
				maxPower = tempPower;
			
			if (tempPower < lowPower)
				lowPower = tempPower;
			
			Term item = new Term(tempPower, tempScalar);
			
			terms.add(item);
		}
	}
	
	/**
	 * Displays the entire polynomial to the screen in the 
	 * proper format.
	 */
	public void printPolynomial() {
		System.out.print(name + " = ");
		
		for (Term currTerm : terms) {
			if (currTerm.getScalar() < 0 || currTerm.getPower() == maxPower) {
				if (Math.abs(currTerm.getPower()) >= 2){
					if (currTerm.getScalar() == 1) {
						System.out.print("X^" + currTerm.getPower() + " ");
					} else {
						System.out.print(currTerm.getScalar() + "X^" + currTerm.getPower() + " ");
					}
				} else if (Math.abs(currTerm.getPower()) == 1) {
					if(currTerm.getScalar() == 1) {
						System.out.print("X ");
					} else {
						System.out.print(currTerm.getScalar() + "X ");
					}
				} else if (Math.abs(currTerm.getPower()) == 0) {
					System.out.print(currTerm.getScalar() + " ");
	
				}
			} else if (currTerm.getScalar() > 0) {
				if (Math.abs(currTerm.getPower()) >= 2){
					if (currTerm.getScalar() == 1) {
						System.out.print("+" + "X^" + currTerm.getPower() + " ");
					} else {
						System.out.print("+" + currTerm.getScalar() + "X^" + currTerm.getPower() + " ");
					}
				} else if (Math.abs(currTerm.getPower()) == 1) {
					if (currTerm.getScalar() == 1) {
						System.out.print("+X ");
					} else {
						System.out.print("+" + currTerm.getScalar() + "X ");
					}
				} else if (Math.abs(currTerm.getPower()) == 0) {
					System.out.print("+" + currTerm.getScalar() + " ");

				}
			}
		}
		System.out.println("\n");
	}

	/**
	 * Accessor for Name
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Accessor for maxPower
	 * @return
	 */
	public int getMaxPower(){
		return maxPower;
	}
	
	/**
	 * Accessor for lowPower
	 * @return
	 */
	public int getLowPower() {
		return lowPower;
	}
	
	/**
	 * Runs a check to see if a given exponential
	 * power exists in the polynomials list of terms.
	 * @param power
	 * @return
	 */
	public boolean hasPower(int power) {
		boolean result = false;
		for (Term currTerm : terms) {
			if (currTerm.getPower() == power)
				result = true;
		}
		return result;
	}
	
	/**
	 * Returns the scalar of a given exponential
	 * power from the polynomials list of terms. 
	 * @param power
	 * @return
	 */
	public int getPowerTerm(int power) {
		int value = 99999;
		for (Term currTerm : terms) {
			if (currTerm.getPower() == power)
				value = currTerm.getScalar();
		}
		return value;
	}
}
