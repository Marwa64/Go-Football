import java.util.*; 
/**
 * <h1>The Playground class</h1>
 * <p> 	This class represents the playground that contains all the slots
 */
public class playground {
	
	private String name, location, description;
	private boolean status;
	private float size;
	private int cancellationPeriod;
	private double price;
	public ArrayList<slot> slots = new ArrayList<slot>();
	/**
	 * Parameterized Constructor that sets the value of all the attributes
	 */
	public playground(String name, String description, String location, float size, int cancellationPeriod, double price) {
		this.name = name;
		this.description = description;
		this.location = location;
		this.size = size;
		this.cancellationPeriod = cancellationPeriod;
		this.price = price;
		status = false;
	}
	/**
	 * The addSlot method add the slot to the slots array list
	 * @param newSlot is the new slot that has been created
	 */
	public void addSlot(slot newSlot) {
		slots.add(newSlot);
	}
	/**
	 * The setStatus method sets the value of status
	 * @param status it is false if the playground isn't active and true if it is
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * @return the status attribute
	 */
	public boolean getStatus() {
		return status;
	}
	/**
	 * The updateInfo method updates all the attributes
	 */
	public void updateInfo(String name, String description, String location, float size, int cancellationPeriod, double price){
		this.name = name;
		this.description = description;
		this.location = location;
		this.size = size;
		this.cancellationPeriod = cancellationPeriod;
		this.price = price;
	}
	/**
	 * @return the name of the playground
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the description of the playground
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the location of the playground
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @return the size of the playground
	 */
	public float getSize() {
		return size;
	}
	/**
	 * @return the cancellation period of the playground
	 */
	public int getCancellationPeriod() {
		return cancellationPeriod;
	}
	/**
	 * @return the price of the playground
	 */
	public double getPrice() {
		return price;
	}
}
