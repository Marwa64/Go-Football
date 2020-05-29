
public class slot {
	
	private String from, to, date, player;
	private boolean available;
	
	public slot(String from, String to, String date){
		this.from = from;
		this.to= to;
		this.date = date;
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
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getPlayer() {
		return player;
	}
}
