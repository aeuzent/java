import java.io.*;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author Alex Euzent
 *
 */
public class Main {

	static ArrayList<Polynomial> active = new ArrayList<Polynomial>();
	static final String DASHES = "-------------------------";
	static final BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean done = false;
		String op1Name = "";
		String op2Name = "";
		String addSub = "";
		String newName = "";
		readFile();
		
		System.out.println(DASHES);
		System.out.println("Polynomial Calculator - Alex Euzent");

		while(!done) {

			System.out.println(DASHES);
			System.out.println("Active Polynomials");
			System.out.println(DASHES + "\n");
			
			for (Polynomial currPoly : active){
				currPoly.printPolynomial();
			}
			System.out.println(DASHES);
			
				try {
					
					System.out.print("Please enter the first polynomial name: ");
					op1Name = keyIn.readLine();
					while (!(checkValid(op1Name, 0))){
						System.out.println(op1Name + " does not exist. Please try again" + "\n");
						System.out.print("Please enter the first polynomial name: ");
						op1Name = keyIn.readLine();
					}
					
					
					System.out.print("Please enter the second polynomial name: ");
					op2Name = keyIn.readLine();
					while (!(checkValid(op2Name, 0))){
						System.out.println(op2Name + " does not exist. Please try again" + "\n");
						System.out.print("Please enter the second polynomial name: ");
						op2Name = keyIn.readLine();
					} 
					
					System.out.print("Please enter the operation (Add or Subtract only): ");
					addSub = keyIn.readLine();
					while (!(checkValid(addSub, 1))){
						System.out.println("Incorrect operation entry. Please use only + for addition and - for subtraction" + "\n");
						System.out.print("Please enter the operation (Add or Subtract only): ");
						addSub = keyIn.readLine();
					}
					
					System.out.print("Please enter the name of the new polynomial: ");
					newName = keyIn.readLine();
					while(checkValid(newName, 0)){
						System.out.println("Name is in use, please select another");
						System.out.print("Please enter the name of the new polynomial: ");
						newName = keyIn.readLine();	
					}
					
				} catch (IOException e) {
					System.err.println("ERROR: Error reading from keyboard");
				}
				

			if (addSub.equals("+")) {
				makePolynomial(op1Name, op2Name, true, newName);
			} else {
				makePolynomial(op1Name, op2Name, false, newName);
			}
			
			System.out.println(DASHES);
			String goAgain = "";
			try {
				System.out.print("Do you want to continue (Y or N)?: ");
				goAgain = keyIn.readLine();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (goAgain.toUpperCase().equals("N"))
				done = true;
		}
		
		
	}
	
	/**
	 * Reads all of the lines of testfile.txt and creates
	 * Polynomial objects that are added to an ArrayList
	 * of active polynomials.
	 */
	public static void readFile() {
		
		String temp = null;
		
		try {
			BufferedReader fileIn = new BufferedReader(new FileReader("testfile.txt"));
			while((temp = fileIn.readLine()) != null) {
				String[] tempPoly = temp.split(" = ");
				Polynomial current = new Polynomial(tempPoly[0], tempPoly[1]);
				active.add(current);
			}
			fileIn.close();
		} catch (FileNotFoundException fne) {
			System.err.println("ERROR: File Not Found");
		} catch (IOException ioe) {
			System.err.println("ERROR: Incorrect File Format");
		}
	}
	
	/**
	 * Checks to see if a given value is valid based on the id.
	 * If the id is 0 then it checks to see if a given name matches
	 * a Polynomial object from the active list. If the id is 1 it 
	 * checks that the given value represents a valid operator.
	 * @param item
	 * @param id
	 * @return
	 */
	private static boolean checkValid(String item, int id) {
		boolean result = false;
		if (id == 0) { //name check
			for (Polynomial currPoly : active){
				if (currPoly.getName().toUpperCase().equals(item.toUpperCase()))
					result = true;
			}
		} if (id == 1) { //operator check
			if(item.equals("+") || item.equals("-"))
				result = true;
		}
		
		return result;
	}
	
	/**
	 * Combines two polynomials based on the given operator into a new one.
	 * Then the method adds the new polynomial to the active list. 
	 * @param polyName1
	 * @param polyName2
	 * @param addSubtract
	 * @param name
	 */
	private static void makePolynomial(String polyName1, String polyName2, boolean addSubtract, String name) {
		String polyString = "";
		Polynomial poly1 = null;
		Polynomial poly2 = null;
		
		for (int x = 0; x < active.size(); x++){
			if (active.get(x).getName().toUpperCase().equals(polyName1.toUpperCase()))
				poly1 = active.get(x);
			
			if (active.get(x).getName().toUpperCase().equals(polyName2.toUpperCase()))
				poly2 = active.get(x);
		}
		
		int topMax = Math.max(poly1.getMaxPower(), poly2.getMaxPower());
		int topLow = Math.min(poly1.getLowPower(), poly2.getLowPower());

		for (int q = topMax; q >= topLow; q--){
			if(poly1.hasPower(q) && poly2.hasPower(q)) {
				if (addSubtract) { //true for add, false for subtract
					polyString += q + " " + (poly1.getPowerTerm(q) + poly2.getPowerTerm(q)) + " ";
				} else {
					polyString += q + " " + (poly1.getPowerTerm(q) - poly2.getPowerTerm(q)) + " ";
				}
			} else if (poly1.hasPower(q)) {
				polyString += q + " " + poly1.getPowerTerm(q) + " ";
			} else if (poly2.hasPower(q)) {
				polyString += q + " " + poly2.getPowerTerm(q) + " ";
			}
		}
		Polynomial newPoly = new Polynomial(name, polyString);
		System.out.print("\n");
		newPoly.printPolynomial();
		active.add(newPoly);
		
	}
}
