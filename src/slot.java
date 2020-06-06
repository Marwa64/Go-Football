/**
 * <h1>The Slot class</h1>
 * <p> 	This class represents the slot that the player can book
 */
public class slot {
	
	private String from, to, date;
	player player;
	private boolean available;
	/**
	 * Default Constructor that initializes all the attributes
	 */
	public slot(String from, String to, String date){
		this.from = from;
		this.to= to;
		this.date = date;
		available = true;
		player = null;
	}
	/**
	 * The book method changes available to false
	 */
	public void book() {
		available = false;
	}
	/**
	 * The unBook method changes available to true and sets the player to null
	 */
	public void unBook() {
		available = true;
		player = null;
	}
	/**
	 * @return the opposite of the available attribute
	 */
	public boolean isBooked() {
		return !(available);
	}
	/**
	 * @return the time the slot begins at
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * @return the time the slot ends at
	 */
	public String getTo() {
		return to;
	}
	/**
	 * @return the date of the slot
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param player is the player that booked this slot
	 */
	public void setPlayer(player player) {
		this.player = player;
	}
	/**
	 * @return the player that booked this slot
	 */
	public player getPlayer() {
		return player;
	}
}
