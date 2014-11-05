/**
 * @filename LMIS.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class LMIS {

	private Library library;
	private ArrayList<ItemOut> checkedOut = new ArrayList<ItemOut>();
	
	/**
	 * Constructor for library
	 */
	public LMIS() {
		library = new Library();
		
	}

	
	
	/**
	 * Processes an input file and only
	 * returns true if the file is valid
	 * @param newFile
	 * @return
	 */
	public boolean inputFile(File newFile){
		boolean result = false;
		
		ArrayList<String> bookIn = new ArrayList<String>();
		ArrayList<String> authIn = new ArrayList<String>();
		ArrayList<String> journIn = new ArrayList<String>();
		String str;
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader(newFile));
		    while ((str = in.readLine()) != null) {
		    	if(str.length() > 0){
		    		if(str.substring(0,1).equals("b")){
		    			bookIn.add(str);
		    		} else if (str.substring(0,1).equals("a")) {
		    			authIn.add(str);
		    		} else if (str.substring(0,1).equals("j")) {
		    			journIn.add(str);
		    		}
		    	}
		    }
		    in.close();
		} catch (IOException e) {
			System.err.println("Error reading file");
		}
		
		if(authIn.size() > 0 && bookIn.size() > 0 && journIn.size() > 0){
			if(parseAuthors(authIn) && parseBooks(bookIn) && parseJournals(journIn)) {
				result = true;
		
			}
		}
		return result;
	}
	
	
	/**
	 * Receives an ArrayList of input strings
	 * that define new authors. Converts them all
	 * to Author objects and adds them to the library
	 * @param authIn
	 * @return
	 */
	private boolean parseAuthors(ArrayList<String> authIn){
		boolean result = false;
		int goodCount = 0;
		
		for(String current : authIn){
			String[] items = current.split(":");
			items = trimAll(items);
			int index = Integer.parseInt(items[1]);
			Author newAuthor = new Author(index, items[2], items[3]);
			if(library.addAuthor(newAuthor)){
				goodCount++;
			}
		}
		
		if(goodCount == authIn.size()){
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Receives an ArrayList of input strings
	 * that define new books. Converts them all to 
	 * Book objects and adds them under the correct
	 * author in the library
	 * @param bookIn
	 * @return
	 */
	private boolean parseBooks(ArrayList<String> bookIn){
		boolean result = false;
		int goodCount = 0;
		
		for(String current : bookIn){
			String[] items = current.split(":");
			items = trimAll(items);
			int index = Integer.parseInt(items[1]);
			int authIndex = Integer.parseInt(items[6]);
			int stock = Integer.parseInt(items[5]);
			double price = Double.parseDouble(items[4]);
			Book newBook = new Book(index, authIndex, items[2], items[3], price, stock);
			if(library.addBook(newBook)){
				goodCount++;
			}
		}
		
		
		if(goodCount == bookIn.size()){
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Receives an ArrayList of input strings
	 * that define new books. Converts them all to 
	 * Book objects and adds them under the correct
	 * author in the library
	 * @param bookIn
	 * @return
	 */
	private boolean parseJournals(ArrayList<String> journalIn){
		boolean result = false;
		int goodCount = 0;
		
		for(String current : journalIn){
			String[] items = current.split(":");
			items = trimAll(items);
			int index = Integer.parseInt(items[1]);
			int volume = Integer.parseInt(items[3]);
			int issue = Integer.parseInt(items[4]);
			int stock = Integer.parseInt(items[6]);
			int authIndex = Integer.parseInt(items[7]);
			Journal newJournal = new Journal(index, authIndex, volume, issue, items[2], items[5], stock);
			if(library.addJournal(newJournal)){
				goodCount++;
			}
		}
		
		
		if(goodCount == journalIn.size()){
			result = true;
		}
		return result;
	}
	
	
	/**
	 * Checks by index to see if the 
	 * given author exists in the library
	 * @param index
	 * @return
	 */
	public boolean hasAuthor(int index){
		boolean result = false;
		if(library.hasAuthor(index)){
			result = true;
		}		
		return result;
	}
	
	
	/**
	 * Returns the author with the given index
	 * @param index
	 * @return
	 */
	public Author getAuthor(int index){
		Author selected = library.getAuthor(index);		
		return selected;
	}
	
	
	/**
	 * Trims arrays of strings 
	 * @param ary
	 * @return
	 */
	private String[] trimAll(String[] ary){
		for(int x=0; x < ary.length; x++){
			ary[x] = ary[x].trim();
		}
		return ary;
	}
	
	
	/**
	 * Returns the whole library as a formatted string
	 */
	public String toString(){
		return library.toString();
	}
	
	
	/**
	 * Checks by index to see if a book
	 * exists in the library
	 * @param bIndex
	 * @return
	 */
	public boolean hasBook(int bIndex){
		boolean result = false;
		if (library.hasBook(bIndex)){
				result = true;
		}
		return result;
	}
					
	
	/**
	 * Returns book with the given index
	 * @param index
	 * @return
	 */
	public Book getBook(int index){
		Book selected = new Book();
		if (library.hasBook(index)){
				selected = library.getBook(index);
		}
		return selected;
	}
	
	
	/**
	 * Returns ArrayList of all books in
	 * a given genre
	 * @param param
	 * @return
	 */
	public ArrayList<Book> getGenre(String param){
		
		ArrayList<Book> out = new ArrayList<Book>();
		for(int x = 0; x < library.totalAuthors(); x++){
			for(int y = 0; y < library.getAuthorArray(x).totalBooks(); y++){
				if(library.getAuthorArray(x).getBookArray(y).getGenre().equals(param)){
					out.add(library.getAuthorArray(x).getBookArray(y));
				}
			}
		}
		return out;
	}
	
	
	/**
	 * Returns ArrayList of authors who's names
	 * contain the given string
	 * @param term
	 * @return
	 */
	public ArrayList<Author> getNameContains(String term){
		library.buildAuthorMap(0);
		ArrayList<Author> out = new ArrayList<Author>();
		TreeMap<String, Author> authorMap = library.getAuthorMap();
		
		for(Map.Entry<String, Author> entry : authorMap.entrySet()){
			String key = entry.getKey();
			Author current = entry.getValue();
			
			if(key.contains(term)){
				out.add(current);
			}
		}
		
		return out;
	}

	
	/**
	 * Returns an ArrayList of books who's
	 * titles contain the given string
	 * @param term
	 * @return
	 */
	public ArrayList<Book> getTitleContains(String term){
		ArrayList<Book> out = new ArrayList<Book>();
		TreeMap<String, Book> bookMap;
		
		for(int x = 0; x < library.totalAuthors(); x++){
			library.getAuthorArray(x).buildBookMap(0);
			bookMap = library.getAuthorArray(x).getBookMap();
			
			for(Map.Entry<String, Book> entry : bookMap.entrySet()){
				String key = entry.getKey();
				Book current = entry.getValue();
				
				if(key.contains(term)){
					out.add(current);
				}
			}
		}
		return out;
	}
	
	
	/**
	 * Returns a DefaultTreeModel to be used in a JTree.
	 * Also loads the correct sort.
	 * @param authTag
	 * @param bookTag
	 * @param journTag
	 * @return
	 */
	public DefaultTreeModel loadTree(int authTag, int bookTag, int journTag){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		DefaultMutableTreeNode authNode, bookNode, journNode;
		library.buildAuthorMap(authTag);
		TreeMap<String, Author> authMap = library.getAuthorMap();
		TreeMap<String, Book> bookMap = new TreeMap<String, Book>();
		TreeMap<Double, Book> bookPriceMap = new TreeMap<Double, Book>();
		TreeMap<Double, Journal> journalMap = new TreeMap<Double, Journal>();
		TreeMap<Date, Journal> journalDateMap = new TreeMap<Date, Journal>();
		
		for(Map.Entry<String, Author> entry : authMap.entrySet()){
			//String key = entry.getKey();
			Author current = entry.getValue();
			
			current.buildBookMap(bookTag);
			current.buildJournalMap(journTag);
			authNode = new DefaultMutableTreeNode(current);
			
			if(bookTag == 1){
				bookPriceMap = current.getBookPriceMap();
				if(bookPriceMap.size()>0){
					DefaultMutableTreeNode bookTop = new DefaultMutableTreeNode("Books");
					for(Map.Entry<Double, Book> entryB : bookPriceMap.entrySet()){
						//Double keyB = entryB.getKey();
						Book currentB = entryB.getValue();
						bookNode = new DefaultMutableTreeNode(currentB);
						bookTop.add(bookNode);
					}
					authNode.add(bookTop);
				}
			} else if (bookTag == 0 || bookTag == 2) {
				bookMap = current.getBookMap();
				if(bookMap.size()>0){
					DefaultMutableTreeNode bookTop = new DefaultMutableTreeNode("Books");
					for(Map.Entry<String, Book> entryB : bookMap.entrySet()){
						//String keyB = entryB.getKey();
						Book currentB = entryB.getValue();
						bookNode = new DefaultMutableTreeNode(currentB);
						bookTop.add(bookNode);
					}
					authNode.add(bookTop);
				}
			}
			
			if(journTag == 0){
				journalMap = current.getJournalVolMap();
				if(journalMap.size()>0){
					DefaultMutableTreeNode journTop = new DefaultMutableTreeNode("Journals");
					for(Map.Entry<Double, Journal> entryJ : journalMap.entrySet()){
						//double keyJ = entryJ.getKey();
						Journal currentJ = entryJ.getValue();
						journNode = new DefaultMutableTreeNode(currentJ);
						journTop.add(journNode);
					}
					authNode.add(journTop);
				}
			} else if (journTag == 1) { 
				journalDateMap = current.getJournalDateMap();
				if(journalDateMap.size()>0){
					DefaultMutableTreeNode journTop = new DefaultMutableTreeNode("Journals");
					for(Map.Entry<Date, Journal> entryJ : journalDateMap.entrySet()){
						//Date keyJ = entryJ.getKey();
						Journal currentJ = entryJ.getValue();
						journNode = new DefaultMutableTreeNode(currentJ);
						journTop.add(journNode);
					}
					authNode.add(journTop);
				}
			}
			
			root.add(authNode);
			bookMap.clear();
			bookPriceMap.clear();
			journalMap.clear();
			journalDateMap.clear();
		}
		
		DefaultTreeModel model = new DefaultTreeModel(root);
		return model;
	}
	
	
	/**
	 * Synchronized method for checking
	 * out books and journals. Used in
	 * threads
	 * @param userId
	 * @param itemIndex
	 * @return
	 */
	public synchronized boolean checkOut(int userId, int itemIndex){
		boolean result = false;

		if(itemIndex/10000 == 1){
			//books
			if(hasBook(itemIndex)){
				Book currBook = getBook(itemIndex);
				if(currBook.checkOut()){
					result = true;
					ItemOut curr = new ItemOut(userId, itemIndex);
					checkedOut.add(curr);
				}
			}
		} else if (itemIndex/10000 == 3){
			//journals
			if(hasJournal(itemIndex)){
				Journal currJourn = getJournal(itemIndex);
				if(currJourn.checkOut()){
					result = true;
					ItemOut curr = new ItemOut(userId, itemIndex);
					checkedOut.add(curr);
				}
			}
		}
		return result;
	}
	
	
	/**
	 * Synchronized method for returning
	 * books and journals. Used in threads
	 * @param userId
	 * @param itemIndex
	 * @return
	 */
	public synchronized boolean bringBack(int userId, int itemIndex){
		boolean result = false;
		boolean exists = false;
		int location = 0;
		for(int x=0; x < checkedOut.size(); x++){
			if(checkedOut.get(x).getUserId() == userId){
				exists = true;
				location = x;
			}
		}
		
		if(exists){
			if(itemIndex/10000 == 1){
				//books
				if(hasBook(itemIndex)){
					Book currBook = getBook(itemIndex);
					if(currBook.bringBack()){
						result = true;
						checkedOut.remove(location);
					}
				}
			} else if (itemIndex/10000 == 3){
				//journals
				if(hasJournal(itemIndex)){
					Journal currJourn = getJournal(itemIndex);
					if(currJourn.bringBack()){
						result = true;
						checkedOut.remove(location);
					}
				}
			}
			
			
		}
		return result;
	}
	
	
	/**
	 * Checks by index to see if a journal
	 * exists in the library
	 * @param index
	 * @return
	 */
	public boolean hasJournal(int index){
		boolean result = false;
		if (library.hasJournal(index)){
				result = true;
		}
		return result;
	}
					
	
	/**
	 * Returns Journal with the given index
	 * @param index
	 * @return
	 */
	public Journal getJournal(int index){
		Journal selected = new Journal();
		if (library.hasJournal(index)){
				selected = library.getJournal(index);
		}
		return selected;
	}
	
	
	/**
	 * Returns a 2d array to be used in creating a JTable
	 * of currently checked out items
	 * @return
	 */
	public String[][] totalOut(){
		
		String out[][] = new String[checkedOut.size()+1][5];
		for(int x = 0; x < checkedOut.size(); x++){
			out[x][0] = Integer.toString(checkedOut.get(x).getUserId());
			int itemIndex = checkedOut.get(x).getItemIndex();
			if(itemIndex/10000 == 1){
				//books
				if(hasBook(itemIndex)){
					Book currBook = getBook(itemIndex);
					out[x][1] = "Book";
					out[x][2] = currBook.getTitle();
					out[x][3] = Integer.toString(currBook.getIndex());
					out[x][4] = Integer.toString(currBook.getAuthIndex());
				}
			} else if (itemIndex/10000 == 3){
				//journals
				if(hasJournal(itemIndex)){
					Journal currJourn = getJournal(itemIndex);
					out[0][1] = "Journal";
					out[0][2] = currJourn.getTitle();
					out[0][3] = Integer.toString(currJourn.getIndex());
					out[0][4] = Integer.toString(currJourn.getAuthIndex());
				}
			}
		}
		return out;
	}
}
