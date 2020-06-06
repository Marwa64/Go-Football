/**
 * <h1>The eWallet class</h1>
 * <p> 	This class represents the wallet that contains the user's money
 */
public class eWallet {
	
	private double balance;
	/**
	 * Default Constructor that initializes the balance
	 */
	public eWallet() {
		balance = 0;
	}
	/**
	 * Parameterized Constructor that sets the value of balance
	 */
	public eWallet(double balance) {
		this.balance = balance;
	}
	/**
	 * The setBalance method sets a new value to the balance
	 * @param balance the new value of the balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	/**
	 * @return the balance of the wallet
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * The withdraw methods reduces the balance based on the parameter
	 * @param amount is the money that will be deducted from the balance
	 */
	public void withdraw(double amount) {
		balance = balance - amount;
	}
	/**
	 * The deposit methods increases the balance based on the parameter
	 * @param amount is the money that will be added to the balance
	 */
	public void deposit(double amount) {
		balance = balance + amount;
	}
}
