import java.util.*;
/**
 * <h1>The Playground Owner class</h1>
 * <p> 	This class represents the playground owner that can add a playground and provide slots that players can book
 */
public class playgroundOwner extends user {
	
	public eWallet wallet = new eWallet();
	public ArrayList<playground> playgrounds = new ArrayList<playground>();
	/**
	 * The addPlayground method adds the new playground to the playground array list
	 * @param newPlayground is the new playground that has been created
	 */
	public void addPlayground(playground newPlayground) {
		playgrounds.add(newPlayground);
	}
	/**
	 * The setBookedSlot method sets a certain slot as booked in a certain playground
	 * @param playgroundIndex is the playground that has the slot that has been booked
	 * @param slotIndex is the slot that has been booked
	 */
	public void setBookedSlot(int playgroundIndex, int slotIndex) {
		playgrounds.get(playgroundIndex).slots.get(slotIndex).book();
	}
	/**
	 * The checkWallet method return the current balance inside the playground owner's wallet
	 * @return the balance inside the wallet
	 */
	public double checkWallet() {
		return wallet.getBalance();
	}
}
