package bankaccount;

import java.sql.Timestamp;

public class Account {
	
	private int accountID;
	private String accountNo;
	private String account_Holder;
	private double balance;
	private Timestamp ts;	
	private int pin;
	
	public Account(){
		
	}
	public Account(int accountID, String accountNo, String account_Holder, double balance, Timestamp ts, int pin) {
		super();
		this.accountID = accountID;
		this.accountNo = accountNo;
		this.account_Holder = account_Holder;
		this.balance = balance;
		this.ts = ts;
		this.pin = pin;
	}


	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccount_Holder() {
		return account_Holder;
	}

	public void setAccount_Holder(String account_Holder) {
		this.account_Holder = account_Holder;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	
	
	

}
