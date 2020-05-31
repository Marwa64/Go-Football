import java.util.*; 
/**
 * <h1>The Player class</h1>
 * <p> 	This class represents the player that can book slot(s) in any playground
 */
public class player extends user{
	
	public eWallet wallet;
	public ArrayList<slot> bookedSlots = new ArrayList<slot>();
    public ArrayList<ArrayList<String> > team = new ArrayList<ArrayList<String>>();
	/**
	 * Default Constructor that initializes the wallet and adds the email array list and name array list to the team array list
	 */
    public player(){
    	wallet = new eWallet();
    	team.add(new ArrayList<String>()); // name row
    	team.add(new ArrayList<String>()); // email row
    }
    /**
     * The bookSlot method adds the booked slot to the bookedSlots array list
     * @param newSlot is the slot that has been booked
     */
	public void bookSlot(slot newSlot) {
		bookedSlots.add(newSlot);
	}
	/**
	 * The cancelBooking method removes the slot from the bookedSlots array list
	 * @param slotToCancel the slot that has been cancelled
	 */
	public void cancelBooking(int slotToCancel) {
		bookedSlots.remove(slotToCancel);
	}
	/**
	 * The addTeammate method adds the name to the first index of the team array list and adds the email to the second one
	 * @param name is the name of the new team mate
	 * @param email is the email of the new team mate
	 */
	public void addTeammate(String name, String email) {
		team.get(0).add(name);
		team.get(1).add(email);
	}
	/**
	 * The removeTeammate method removes the name and email of a certain team mate from the team array list
	 * @param index is the index of the team mates that is to be removed
	 */
	public void removeTeammate(int index) {
		team.get(0).remove(index);
		team.get(1).remove(index);
	}
	/**
	 * The checkWallet method return the current balance inside the player's wallet
	 * @return the balance inside the wallet
	 */
	public double checkWallet() {
		return wallet.getBalance();
	}
}
