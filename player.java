import java.util.*; 

public class player extends user{
	
	private eWallet wallet;
	public ArrayList<slot> bookedSlots = new ArrayList<slot>();
    public ArrayList<ArrayList<String> > team = new ArrayList<ArrayList<String>>();
	
    public player(){
    	wallet = new eWallet();
    	team.add(new ArrayList<String>()); // name row
    	team.add(new ArrayList<String>()); // email row
    }
	public void bookSlot(slot newSlot) {
		bookedSlots.add(newSlot);
	}
	public void cancelBooking(slot slotToCancel) {
		bookedSlots.remove(slotToCancel);
	}
	public void addTeammate(String name, String email) {
		team.get(0).add(name);
		team.get(1).add(email);
	}
	public void removeTeammate(int index) {
		team.get(0).remove(index);
		team.get(1).remove(index);
	}
	public double checkWallet() {
		return wallet.getBalance();
	}
}
