/**
 * @filename Library.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
import java.util.ArrayList;
import java.util.TreeMap;

public class Library {

	private ArrayList<Author> authors;
	private TreeMap<String, Author> authorMap;
	
	
	/**
	 * Constructor for library
	 */
	public Library() {
		authors = new ArrayList<Author>();
		authorMap = new TreeMap<String, Author>();
	}
	

	/**
	 * Adds new author to library
	 * if they don't already exist
	 * @param newAuthor
	 * @return
	 */
	public boolean addAuthor(Author newAuthor) {
		boolean result = false;
		if(!hasAuthor(newAuthor)){
			authors.add(newAuthor);
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Checks if author exists by index
	 * @param index
	 * @return
	 */
	public boolean hasAuthor(Author newAuthor){
		boolean result = false;
		for(Author current : authors){
			if (current.equals(newAuthor)){
				result = true;
			}
		}
		return result;
	}
	
		
	/**
	 * Returns author, used for traversing
	 * whole ArrayList of authors
	 * @param x
	 * @return
	 */
	public Author getAuthorArray(int x){
		return authors.get(x);
	}
	
	
	/**
	 * Returns size of ArrayList of authors
	 * @return
	 */
	public int totalAuthors(){
		return authors.size();
	}

	
	/**
	 * Checks to see if author index exists
	 * @param index
	 * @return
	 */
	public boolean hasAuthor(int index){
		boolean result = false;
		for (Author current : authors){
			if (current.getIndex() == index){
				result = true;
			}
		}
		return result;
	}

	
	/**
	 * Returns author by index
	 * @param index
	 * @return
	 */
	public Author getAuthor(int index){
		Author selected = new Author();
		for (Author current : authors){
			if (current.getIndex() == index){
				selected = current;
			}
		}
		return selected;
	}

	
	/**
	 * Adds book to library under the correct author
	 * if it doesn't already exist
	 * @param newBook
	 * @return
	 */
	public boolean addBook(Book newBook) {
		boolean result = false;
		if(hasAuthor(newBook.getAuthIndex())){
			result = getAuthor(newBook.getAuthIndex()).addBook(newBook);
		}
		return result;
	}

	
	/**
	 * Checks to see if book 
	 * exists in library
	 * @param newBook
	 * @return
	 */
	public boolean hasBook(Book newBook){
		boolean result = false;
		for(Author current : authors){
			if(current.hasBook(newBook)){
				result = true;
			}
		}
		
		return result;
	}
	
	
	/**
	 * Checks by book index
	 * to see if book exists in
	 * library
	 * @param index
	 * @return
	 */
	public boolean hasBook(int index){
		boolean result = false;
		for (Author current : authors){
			if (current.hasBook(index)){
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * Returns book with given index
	 * @param index
	 * @return
	 */
	public Book getBook(int index){
		Book selected = new Book();
		for (Author current : authors){
			if (current.hasBook(index)){
				selected = current.getBook(index);
			}
		}
		return selected;
	}
	
	
	/**
	 * Returns string of all authors
	 * in the library
	 */
	public String toString(){
		String out = "";
		for(Author current : authors){
			out += current.toStringB() + "\n";
		}
		return out;
	}
	
	
	/**
	 * Build selected TreeMap of Authors
	 * @param tag
	 */
	public void buildAuthorMap(int tag){
		authorMap.clear();
		String key = "";
		for(Author current : authors){
			switch(tag){
				case 0: key = current.getName();
						break;
				
				case 1: key += current.getIndex();
						break;
			}
			authorMap.put(key, current);
		}
	}
	
	
	/**
	 * Accessor for authorMap
	 * @return
	 */
	public TreeMap<String, Author> getAuthorMap(){
		return authorMap;
	}

	
	/**
	 * Adds journal to library under the correct author
	 * if it doesn't already exist
	 * @param newJournal
	 * @return
	 */
	public boolean addJournal(Journal newJournal) {
		boolean result = false;
		if(hasAuthor(newJournal.getAuthIndex())){
			result = getAuthor(newJournal.getAuthIndex()).addJournal(newJournal);
		}
		return result;
	}
	
	
	/**
	 * Checks to see if journal 
	 * exists in library
	 * @param newJournal
	 * @return
	 */
	public boolean hasJournal(Journal newJournal){
		boolean result = false;
		for(Author current : authors){
			if(current.hasJournal(newJournal)){
				result = true;
			}
		}
		
		return result;
	}
	
	
	/**
	 * Checks by journal index
	 * to see if journal exists in
	 * library
	 * @param index
	 * @return
	 */
	public boolean hasJournal(int index){
		boolean result = false;
		for (Author current : authors){
			if (current.hasJournal(index)){
				result = true;
			}
		}
		return result;
	}
	
	
	/**
	 * Returns journal with given index
	 * @param index
	 * @return
	 */
	public Journal getJournal(int index){
		Journal selected = new Journal();
		for (Author current : authors){
			if (current.hasJournal(index)){
				selected = current.getJournal(index);
			}
		}
		return selected;
	}
	
}
