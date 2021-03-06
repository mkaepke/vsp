package haw.vs.VSPraktikum.util.Bank;

public class Account {
	/**
	 * unique accountnumber
	 */
	private int accountNumber;
	/**
	 * saldo of a player
	 */
	private int saldo;
	/**
	 * URIs of different resources
	 */
//	private String bankURI;
//	private String playerURI;
//	private String accountURI;
	
	private String player;
	
	public Account(int accountNumber, int saldo, String player) {//, String bankURI, String playerURI) {
		this.accountNumber = accountNumber;
		this.saldo = saldo;
		this.player = player;
//		this.bankURI = bankURI;
//		this.playerURI = playerURI;
	}
	
	public Account(int accountNumber) {
		this.accountNumber = accountNumber;
		this.saldo = Integer.MAX_VALUE / 2;
		this.player = "BankAccount";
	}
	
	public void addAmount(int amount) {
		this.saldo += amount;
	}
	
	public boolean subtractAmount(int amount) {
		if((saldo - amount) < 0) {
			return false;
		} else {
			saldo -= amount;
			return true;
		}
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public int getSaldo() {
		return saldo;
	}
	
	public String getPlayer() {
		return player;
	}
}
