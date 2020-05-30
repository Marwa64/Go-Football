
public class slot {
	
	private String from, to, date;
	player player;
	private boolean available;
	
	public slot(String from, String to, String date){
		this.from = from;
		this.to= to;
		this.date = date;
		available = true;
		player = null;
	}
	public void book() {
		available = false;
	}
	public void unBook() {
		available = true;
		player = null;
	}
	public boolean isBooked() {
		return !(available);
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public String getDate() {
		return date;
	}
	public void setPlayer(player player) {
		this.player = player;
	}
	public player getPlayer() {
		return player;
	}
}
