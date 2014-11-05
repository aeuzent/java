import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @filename Journal.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
public class Journal {

	private int index, authIndex, volume, issue, inInventory, inStock;
	private String title;
	private Date pubDate;
	private	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

	
	/**
	 * Empty constructor for Journal
	 */
	public Journal() {
		index = 0; 
		authIndex = 0;
		volume = 0;
		issue = 0;
		title = "";
		pubDate = new Date();
		inInventory = 0;
		inStock = 0;
	}
	
	
	/**
	 * Constructor for Journal
	 * @param newIndex
	 * @param newAuthIndex
	 * @param newVolume
	 * @param newIssue
	 * @param newTitle
	 * @param newPubDate
	 */
	public Journal(int newIndex, int newAuthIndex, int newVolume, int newIssue, String newTitle, String newPubDate, int totalBooks){
		index = newIndex; 
		authIndex = newAuthIndex;
		volume = newVolume;
		issue = newIssue;
		title = newTitle;
		
		Date newDate;
		try {
			newDate = makeDate(newPubDate);
			pubDate = newDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * Mutator for volume
	 * @param newIndex
	 */
	public void setVolume(int newVolume){
		volume = newVolume; 
	}
	
	
	/**
	 * Accessor for volume
	 * @return
	 */
	public int getVolume(){
		return volume;
	}	
	
	
	/**
	 * Mutator for issue
	 * @param newIndex
	 */
	public void setIssue(int newIssue){
		issue = newIssue; 
	}
	
	
	/**
	 * Accessor for issue
	 * @return
	 */
	public int getIssue(){
		return issue;
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
	 * Mutator for pubDate
	 * @param newGenre
	 */
	public void setPubDate(String newPubDate){
		Date newDate;
		try {
			newDate = makeDate(newPubDate);
			pubDate = newDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * Returns publish date as formatted string
	 * @return
	 */
	public String getPubDateStr(){
		String out = df.format(pubDate);
		return out;
	}
	
	
	/**
	 * Accessor for pubDate
	 * @return
	 */
	public Date getPubDate(){
		return pubDate;
	}
	
	
	/**
	 * Formats and returns complete journal object as a string
	 */
	public String toStringB(){
		return index + " : " + title  + " : " + volume  + " : " + issue  + " : " + 
				df.format(pubDate)  + " : " + inStock + "/" + inInventory + " copies : " + authIndex;
	}
	
	
	/**
	 * Formats and returns journal object as a string
	 */
	public String toString(){
		return volume  + "." + issue  + " " + title;
	}

	
	/**
	 * Check to see if journal
	 * equals current object
	 * @param test
	 * @return
	 */
	public boolean equals(Journal test){
		boolean result = false;
		if(index == test.getIndex()){
			if(authIndex == test.getAuthIndex()){
				if(volume == test.getVolume()){
					if(issue == test.getIssue()){
						if(title.equals(test.getTitle())){
							if(pubDate.equals(test.getPubDate())){
								result = true;
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Creates a date object from 
	 * a formatted string
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public Date makeDate(String dateStr) throws ParseException{
		Date newDate = df.parse(dateStr);
		return newDate;
	}
	
	
	/**
	 * Returns journal and issue listed
	 * as a single number
	 * @return
	 */
	public double journIssue(){
		return volume + (issue*0.1);
	}
	
	
	/**
	 * Synchronized method for checking
	 * out journals. Used in threads
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
	 * journals. Used in threads
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
	 * Returns total number of journals,
	 * including those checked out
	 * @return
	 */
	public int getTotalJournals(){
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
