
public class eWallet {
	
	private double balance;
	
	public eWallet() {
		balance = 0;
	}
	public eWallet(double balance) {
		this.balance = balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBalance() {
		return balance;
	}
	public void withdraw(double amount) {
		balance = balance - amount;
	}
	public void deposit(double amount) {
		balance = balance + amount;
	}
}
