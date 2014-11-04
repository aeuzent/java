import java.io.*;

/**
 * @author Alex Euzent
 *
 */
public class Main {

	static final String DASHES = "---------------------------------";
	static Sorter sortNow = new Sorter();


	public static void main(String[] args) {
		
		BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
		int[] mainArray = sortNow.makeArray(100, 999);
		int[] testArray = mainArray.clone();
		
		System.out.println(DASHES);
		System.out.println("Sort Benchmark - Alex Euzent");
		System.out.println(DASHES);

		System.out.println("Current Array of \n100 Random Numbers");
		System.out.println(DASHES);
		//print 100 array
		printArray(mainArray);
		
		System.out.println(DASHES);
		System.out.println("Results of Sorting with Option 1");
		System.out.println(DASHES);
		//print 100 array
		printArray(sortNow.QuickSortOpt1(testArray));
		
		testArray = mainArray.clone();
		
		System.out.println(DASHES);
		System.out.println("Results of Sorting with Option 2");
		System.out.println(DASHES);
		//print 100 array
		printArray(sortNow.QuickSortOpt2(testArray));

		
		System.out.println("Hit ENTER to continue...");
		try {
			String whoCares = keyIn.readLine();
		} catch (IOException e) {
			System.err.println("How did you manage to do this?");
		}
		
		System.out.println(DASHES);
		System.out.println("Measured Average Execution Time");
		//build table here
		printTimeTable(benchmark());
		
		
		
	}

	
	/**
	 * Prints a given array of integer values with
	 * ten to a line.
	 * @param array
	 */
	private static void printArray(int[] array){
		for(int x=0; x < array.length; x++){
			if((x%10)==0){
				System.out.print("\n");
			}
			
			System.out.format("%5d", array[x]);
		}
		System.out.println("\n");
	}
	
	
	/**
	 * Prints a formatted listing of the benchmark results
	 * @param results
	 */
	private static void printTimeTable(long[] results){
		System.out.format("+-------------------------+--------------------------------------------+%n");
		System.out.format("|    Algorithm            |      Average Execution time for 10 Runs    |%n");
		System.out.format("+                         +-------------+--------------+---------------+%n");
		System.out.format("|                         | SIZE = 100  | SIZE = 1,000 | SIZE = 10,000 |%n");
		System.out.format("|                         | MAX = 999   | MAX = 9,999  | MAX = 99,999  |%n");
		System.out.format("+-------------------------+-------------+--------------+---------------+%n");
		System.out.format("| QuickSort               | %9d   | %11d  | %12d  |%n", results[0], results[1], results[2]);
		System.out.format("+-------------------------+-------------+--------------+---------------+%n");
		System.out.format("| QuickSortOpt1           | %9d   | %11d  | %12d  |%n", results[3], results[4], results[5]);
		System.out.format("+-------------------------+-------------+--------------+---------------+%n");
		System.out.format("| QuickSortOpt2           | %9d   | %11d  | %12d  |%n", results[6], results[7], results[8]);
		System.out.format("+-------------------------+-------------+--------------+---------------+%n");

	}
	
	/**
	 * Runs the chosen sort 10 times and returns the 
	 * average run time. 
	 * @param size
	 * @param max
	 * @param choice
	 * @return
	 */
	private static long averageTime(int size, int max, int choice){
		int[] mainArray = sortNow.makeArray(size, max);
		int[] testArray = mainArray.clone();
		long sum=0;
		
		for(int i=0; i<10; i++){
			sum += sortNow.clockSort(testArray, choice);
			testArray = mainArray.clone();
		}
		
		sum = sum/10;
		return sum;
	}
	
	
	/**
	 * Performs each run time test for all three sort algorithms
	 * with 3 different options for size and max value then returns
	 * the results in an array. 
	 * @return
	 */
	private static long[] benchmark(){
		long[] results = new long[9];
		
		for(int x=0; x<9; x++){
			if(x%3 == 0){
				results[x] = averageTime(100, 999, (x/3));
			} else if (x%3 == 1) {
				results[x] = averageTime(1000, 9999, (x/3));
			} else if (x%3 == 2) {
				results[x] = averageTime(10000, 99999, (x/3));
			}
		}
		return results;
	}
}
