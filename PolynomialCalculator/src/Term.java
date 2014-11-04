/**
 * @author Alex Euzent
 * Term class represents a single term in a 
 * polynomial with a scalar multiple and the 
 * exponential power.
 */
public class Term {

	int scalar;
	int power;
	
	/**
	 * Constructor creates new Term object
	 * from given values.
	 * @param newPower
	 * @param newScalar
	 */
	public Term(int newPower, int newScalar) {
		scalar = newScalar;
		power = newPower;
	}
	
	/**
	 * Accessor for scalar
	 * @return
	 */
	public int getScalar(){
		return scalar;		
	}
	
	/**
	 * Accessor for power
	 * @return
	 */
	public int getPower() {
		return power;
	}

}
