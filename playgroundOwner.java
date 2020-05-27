import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class playgroundOwner extends user {
	
	public eWallet wallet = new eWallet();
	public ArrayList<playground> playgrounds = new ArrayList<playground>();
	
	public void addPlayground(playground newPlayground) {
		playgrounds.add(newPlayground);
	}
	public void setBookedSlot(int playgroundIndex, int slotIndex) {
		playgrounds.get(playgroundIndex).slots.get(slotIndex).book();
	}
	public double checkWallet() {
		return wallet.getBalance();
	}
}
