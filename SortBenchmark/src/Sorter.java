/**
 * @author Alex Euzent
 *
 */
public class Sorter {

	/**
	 * makeArray creates and returns an array of the
	 * specified size and populates it with random 
	 * numbers between 0 and the max.
	 * @param size
	 * @param max
	 * @return
	 */
	public int[] makeArray(int size, int max) {
		int[] newArray = new int[size];
		for(int i=0; i < newArray.length; i++){
			newArray[i] = 1 + (int)(Math.random()*max);
		}
		return newArray;
	}
	

	/**
	 * QuickSorts the given array until the limit is 
	 * reached. The limit is defined as the maximum 
	 * size a sub-partition of the QuickSort can be. 
	 * @param array
	 * @param low
	 * @param high
	 * @param limit
	 * @return
	 */
	public int[] QuickSort (int[] array, int low, int high, int limit) {
		int newLow = low;
		int newHigh = high;
		
		int pivot = array[low + (high-low)/2];
		
		if(!((high-low)%limit == 0)) {
			while(newLow <= newHigh){
				while (array[newLow] < pivot){
					newLow++;
				}
			
				while (array[newHigh] > pivot) {
					newHigh--;
				}
				
				if (newLow <= newHigh) {
					array = swap(array, newLow, newHigh);
					newLow++;
					newHigh--;
				}				
			}
			
			if (low < newHigh) {
				array = QuickSort(array, low, newHigh, limit);
			}
			if (newLow < high) {
				array = QuickSort(array, newLow, high, limit);
			}
		}
		return array;
	}
	
	
	/**
	 * Runs QuickSort till the limit on partition
	 * size is reached then completes the sort using 
	 * insertion sort.
	 * @param array
	 * @return
	 */
	public int[] QuickSortOpt1 (int[] array) {
		QuickSort(array, 0, array.length-1, 10);
		int q = 0;
		
		for (int i=0; i< array.length; i++) {
			int current = array[i];
			for(q = i -1; q >=0 && current < array[q]; q--){
				array[q+1] = array[q];
			}
			array[q+1] = current;
		}
		return array;
	}
	
	
	/**
	 * Runs QuickSort till the limit on partition
	 * size is reached then completes the sort using 
	 * bubble sort.
	 * @param array
	 * @return
	 */
	public int[] QuickSortOpt2 (int[] array) {
		boolean done = false;
		QuickSort(array, 0, array.length-1, 10);
		
		while(!done){
			done = true;
			for(int x=0; x< array.length; x++){
				if(!(x == array.length-1)){
					if ((array[x+1] < array[x])){
						array = swap(array, x, x+1);
						done = false;
					}
				}
			}
		}	
		return array;
	}
	
	
	/**
	 * Swaps two elements in the given array at the specified indexes
	 * @param array
	 * @param indexA
	 * @param indexB
	 * @return
	 */
	private int[] swap(int[] array, int indexA, int indexB){
		int temp = array[indexA];
		array[indexA] = array[indexB];
		array[indexB] = temp;
		return array;
	}
	
	
	/**
	 * Records the run time of a given sort and returns it
	 * @param array
	 * @param choice
	 * @return
	 */
	public long clockSort(int[] array, int choice){
		long start = 0;
		long stop = 0;
		switch (choice){
			case 0: start = System.nanoTime();
					QuickSort(array, 0, array.length-1, 999999);
					stop = System.nanoTime();
					break;
					
			case 1:	start = System.nanoTime();
					QuickSortOpt1(array);
					stop = System.nanoTime();
					break;
					
			case 2: start = System.nanoTime();
					QuickSortOpt2(array);
					stop = System.nanoTime();
					break;
			
			default:
					break;
		}
		return (stop - start);
	}
}
