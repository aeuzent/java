/**
 * @filename Book.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
public class Book {

	private int index, authIndex, inInventory, inStock;
	private double price;
	private String title, genre;
	
	
	/**
	 * Empty constructor for Book
	 */
	public Book() {
		index = 0; 
		authIndex = 0;
		price = 0;
		title = "";
		genre = "";
		inInventory = 0;
		inStock = 0;
	}
	
	
	/**
	 * Constructor for book
	 * @param newIndex
	 * @param newAuthIndex
	 * @param newTitle
	 * @param newGenre
	 */
	public Book(int newIndex, int newAuthIndex, String newTitle, String newGenre, double newPrice, int totalBooks){
		index = newIndex; 
		authIndex = newAuthIndex;
		price = newPrice;
		title = newTitle;
		genre = newGenre;
		inInventory = totalBooks;
		inStock = inInventory;
	}
	
	
	/**
	 * Mutator for index
	 * @param newIndex
	 */
	public void setIndex(int newIndex){
		index = newIndex; 
	}
	
	
	/**
	 * Accessor for index
	 * @return
	 */
	public int getIndex(){
		return index;
	}
	
	
	/**
	 * Mutator for author index
	 * @param newAuthIndex
	 */
	public void setAuthIndex(int newAuthIndex){
		authIndex = newAuthIndex;
	}
	
	
	/**
	 * Accessor for author index
	 * @return
	 */
	public int getAuthIndex(){
		return authIndex;
	}
	
	
	/**
	 * Mutator for title
	 * @param newTitle
	 */
	public void setTitle(String newTitle){
		title = newTitle;
	}
	
	
	/**
	 * Accessor for title
	 * @return
	 */
	public String getTitle(){
		return title;
	}
	
	
	/**
	 * Mutator for genre
	 * @param newGenre
	 */
	public void setGenre(String newGenre){
		genre = newGenre;
	}
	
	
	/**
	 * Accessor for genre
	 * @return
	 */
	public String getGenre(){
		return genre;
	}
	
	
	/**
	 * Formats and returns complete book object as a string
	 */
	public String toStringB(){
		return index + " : " + title  + " : " + genre + " : " + price + " : " + 
				inStock + "/" + inInventory + " copies : " + authIndex;
	}
	
	
	/**
	 * Formats and returns book object as a string
	 */
	public String toString(){
		return title;
	}
	
	
	/**
	 * Checks if two book objects
	 * are equal
	 * @param test
	 * @return
	 */
	public boolean equals(Book test){
		boolean result = false;
		if(index == test.getIndex()){
			if(authIndex == test.getAuthIndex()){
				if(title.equals(test.getTitle())){
					if(genre.equals(test.getGenre())){
						result = true;
					}
				}
			}
		}
		return result;
	}
	

	/**
	 * Mutator for price
	 * @param newPrice
	 */
	public void setPrice(int newPrice){
		price = newPrice; 
	}
	
	
	/**
	 * Accessor for price
	 * @return
	 */
	public double getPrice(){
		return price;
	}
	
	
	/**
	 * Synchronized method for checking
	 * out books. Used in threads
	 * @return
	 */
	public synchronized boolean checkOut(){
		boolean result = false;
		if(inStock > 0){
			inStock--;
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Synchronized method for returning
	 * books. Used in threads
	 * @return
	 */
	public synchronized boolean bringBack(){
		boolean result = false;
		
		if(inStock < inInventory){
			inStock++;
			result = true;
		}
		
		return result;
	}
	
	
	/**
	 * Returns total number of books,
	 * including those checked out
	 * @return
	 */
	public int getTotalBooks(){
		return inInventory;
	}
	
	
	/**
	 * Returns the number of copies available
	 * @return
	 */
	public int getStock(){
		return inStock;
	}
}
