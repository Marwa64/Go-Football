import java.util.*; 

public class playground {
	
	private String name, location, description;
	private boolean status;
	private float size;
	private int cancellationPeriod;
	private double price;
	public ArrayList<slot> slots = new ArrayList<slot>();
	
	public playground(String name, String description, String location, float size, int cancellationPeriod, double price) {
		this.name = name;
		this.description = description;
		this.location = location;
		this.size = size;
		this.cancellationPeriod = cancellationPeriod;
		this.price = price;
		status = false;
	}
	public void addSlot(slot newSlot) {
		slots.add(newSlot);
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean getStatus() {
		return status;
	}
	public void updateInfo(String name, String description, String location, float size, int cancellationPeriod, double price){
		this.name = name;
		this.description = description;
		this.location = location;
		this.size = size;
		this.cancellationPeriod = cancellationPeriod;
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getLocation() {
		return location;
	}
	public float getSize() {
		return size;
	}
	public int getCancellationPeriod() {
		return cancellationPeriod;
	}
	public double getPrice() {
		return price;
	}
}
