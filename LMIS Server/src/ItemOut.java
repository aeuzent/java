/**
 * @filename Journal.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
public class ItemOut {
	
	private int userId, itemIndex;
	
	
	/**
	 * Default constructor for ItemOut
	 */
	public ItemOut() {
		userId = 0;
		itemIndex = 0;
	}

	
	/**
	 * Constructor for ItemOut with
	 * new parameters
	 * @param userId
	 * @param itemIndex
	 */
	public ItemOut(int userId, int itemIndex) {
		this.userId = userId;
		this.itemIndex = itemIndex;
	}
	
	
	/**
	 * Accessor for userId
	 * @return
	 */
	public int getUserId(){
		return userId;
	}
	
	
	/**
	 * Mutator for userId
	 * @param userId
	 */
	public void setUserId(int userId){
		this.userId = userId;
	}

	
	/**
	 * Accessor for itemIndex
	 * @return
	 */
	public int getItemIndex(){
		return itemIndex;
	}
	
	
	/**
	 * Mutator for itemIndex
	 * @param itemIndex
	 */
	public void setItemIndex(int itemIndex){
		this.itemIndex = itemIndex;
	}
		
}
