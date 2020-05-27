
public class slot {
	
	private String from, to, player;
	private boolean available;
	
	public slot(String from, String to){
		this.from = from;
		this.to= to;
		available = true;
		player= "";
	}
	public void book() {
		available = false;
	}
	public void unBook() {
		available = true;
	}
	public boolean isBooked() {
		return available;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getPlayer() {
		return player;
	}
}
