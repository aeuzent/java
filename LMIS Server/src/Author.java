/**
 * @filename Author.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
import java.util.*;

public class Author {

	private int index;
	private String name, address;
	private ArrayList<Book> books;
	private ArrayList<Journal> journals;
	private TreeMap<String, Book> bookMap;
	private TreeMap<Double, Book> bookPriceMap;
	private TreeMap<Double, Journal> journalVolMap;
	private TreeMap<Date, Journal> journalDateMap;
	
	
	/**
	 * Empty constructor for Author
	 */
	public Author() {
		index = 0;
		name = "";
		address = "";
		books = new ArrayList<Book>();
		journals = new ArrayList<Journal>();
		bookMap = new TreeMap<String, Book>();
		bookPriceMap = new TreeMap<Double, Book>();
		journalVolMap = new TreeMap<Double, Journal>();
		journalDateMap = new TreeMap<Date, Journal>();
	}

	
	/**
	 * Constructor for Author
	 * @param newIndex
	 * @param newName
	 * @param newAddress
	 */
	public Author(int newIndex, String newName, String newAddress){
		index = newIndex;
		name = newName;
		address = newAddress;
		books = new ArrayList<Book>();
		journals = new ArrayList<Journal>();
		bookMap = new TreeMap<String, Book>();
		bookPriceMap = new TreeMap<Double, Book>();
		journalVolMap = new TreeMap<Double, Journal>();
		journalDateMap = new TreeMap<Date, Journal>();
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
	 * Mutator for name
	 * @param newName
	 */
	public void setName(String newName){
		name = newName;
	}
	
	
	/**
	 * Accessor for name
	 * @return
	 */
	public String getName(){
		return name;
	}
	
	
	/**
	 * Mutator for address
	 * @param newAddress
	 */
	public void setAddress(String newAddress){
		address = newAddress;
	}
	
	
	/**
	 * Accessor for address
	 * @return
	 */
	public String getAddress(){
		return address;
	}
	
	
	/**
	 * Checks equality between author objects
	 * @param test
	 * @return
	 */
	public boolean equals(Author test){
		boolean result = false;
		if(index == test.getIndex()){
			if(name.equals(test.getName())){
				if(address.equals(test.getAddress())){
					result = true;
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Adds a book under this author if
	 * book does not exist
	 * @param newBook
	 * @return
	 */
	public boolean addBook(Book newBook){
		boolean result = false;
		if(!hasBook(newBook)){
			books.add(newBook);
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Checks to see if a book exists
	 * @param newBook
	 * @return
	 */
	public boolean hasBook(Book newBook){
		boolean result = false;
		
		for(Book current : books){
			if(current.equals(newBook)){
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * Returns book, used for traversing 
	 * whole ArrayList of books
	 * @param x
	 * @return
	 */
	public Book getBookArray(int x){
		return books.get(x);
	}
	
	/**
	 * Returns length of ArrayList
	 * of books
	 * @return
	 */
	public int totalBooks(){
		return books.size();
	}
	
	
	/**
	 * Checks to see if book index exists
	 * @param index
	 * @return
	 */
	public boolean hasBook(int index){
		boolean result = false;
		for (Book current : books){
			if (current.getIndex() == index){
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * Returns book by index
	 * @param index
	 * @return
	 */
	public Book getBook(int index){
		Book selected = new Book();
		for (Book current : books){
			if (current.getIndex() == index){
				selected = current;
			}
		}
		return selected;
	}
	
	
	/**
	 * Formats and returns complete author object as 
	 * a string along with all of their books
	 */
	public String toStringB(){
		String out = index  + " : " + name  + " : " + address + "\n";
		if(totalBooks()>0){
			out += "    Books: \n"; 
			for(Book current: books){
				out += "\t" + current.toStringB() + "\n";
			}
		}
		if(totalJournals()>0){
			out += "    Journals: \n";
			for(Journal current: journals){
				out += "\t" + current.toStringB() + "\n";
			}
		}
		
		return out;
	}
	
	
	/**
	 * Adds a journal under this author if
	 * journal does not exist
	 * @param newJournal
	 * @return
	 */
	public boolean addJournal(Journal newJournal){
		boolean result = false;
		if(!hasJournal(newJournal)){
			journals.add(newJournal);
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Checks to see if title/genre exists
	 * 0 for title, 1 for genre
	 * @param param
	 * @param tag
	 * @return
	 */
	public boolean hasJournal(Journal newJournal){
		boolean result = false;
		
		for(Journal current : journals){
			if(current.equals(newJournal)){
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * Returns journal, used for traversing 
	 * whole ArrayList of journals
	 * @param x
	 * @return
	 */
	public Journal getJournalArray(int x){
		return journals.get(x);
	}
	
	
	/**
	 * Returns length of ArrayList
	 * of journals
	 * @return
	 */
	public int totalJournals(){
		return journals.size();
	}
	
	
	/**
	 * Checks to see if journal index exists
	 * @param index
	 * @return
	 */
	public boolean hasJournal(int index){
		boolean result = false;
		for (Journal current : journals){
			if (current.getIndex() == index){
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * Returns journal by index
	 * @param index
	 * @return
	 */
	public Journal getJournal(int index){
		Journal selected = new Journal();
		for (Journal current : journals){
			if (current.getIndex() == index){
				selected = current;
			}
		}
		return selected;
	}
	
	
	/**
	 * Creates selected TreeMap of Books
	 * @param tag
	 */
	public void buildBookMap(int tag){
		bookMap.clear();
		bookPriceMap.clear();
		for(Book current : books){
			switch(tag){
				case 0: bookMap.put(current.getTitle(), current);
						break;
						
				case 1: bookPriceMap.put(current.getPrice(), current);
						break;
						
				case 2: String temp = new Integer(current.getIndex()).toString();
						bookMap.put(temp, current);
						break;
			}

		}
	}
	
	
	/**
	 * Creates selected TreeMap of Journals
	 * @param tag
	 */
	public void buildJournalMap(int tag){
		journalVolMap.clear();
		journalDateMap.clear();
		for(Journal current : journals){
			switch(tag){
				case 0: journalVolMap.put(current.journIssue(), current);
						break;
				case 1: journalDateMap.put(current.getPubDate(), current);
						break;
			}
			
		}
		
	}
	

	/**
	 * Accessor for bookMap
	 * @return
	 */
	public TreeMap<String, Book> getBookMap(){
		return bookMap;
	}
	
	
	/**
	 * Accessor for bookPriceMap
	 * @return
	 */
	public TreeMap<Double, Book> getBookPriceMap(){
		return bookPriceMap;
	}
	
	
	/**
	 * Accessor for journalVolMap
	 * @return
	 */
	public TreeMap<Double, Journal> getJournalVolMap(){
		return journalVolMap;
	}
	
	
	/**
	 * Accessor for journalDateMap
	 * @return
	 */
	public TreeMap<Date, Journal> getJournalDateMap(){
		return journalDateMap;
	}
	

	/**
	 * Formats and returns author object as 
	 * a string 
	 */
	public String toString(){
		return name;
	}
}
