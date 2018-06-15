package com.cg.banking.bean;
import java.util.HashMap;
public class Account {
	private int accountNo,pinNumber,pinCounter;
	private float accountBalance;
	private String accountType, status;
	private HashMap<Integer, Transaction> transactions=new HashMap<>();
	private int transactionIdCounter=101;
	private int transactionIdxCounter=0;
	public Account(){}
	public Account(float accountBalance, String accountType) {
		super();
		this.accountBalance = accountBalance;
		this.accountType = accountType;
	}
	public Account(int accountNo, int pinNumber, int pinCounter, float accountBalance, String accountType,
			String status, HashMap<Integer, Transaction> transactions, int transactionIdCounter,
			int transactionIdxCounter) {
		super();
		this.accountNo = accountNo;
		this.pinNumber = pinNumber;
		this.pinCounter = pinCounter;
		this.accountBalance = accountBalance;
		this.accountType = accountType;
		this.status = status;
		this.transactions = transactions;
		this.transactionIdCounter = transactionIdCounter;
		this.transactionIdxCounter = transactionIdxCounter;
	}
	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	public int getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}
	public int getPinCounter() {
		return pinCounter;
	}
	public void setPinCounter(int pinCounter) {
		this.pinCounter = pinCounter;
	}
	public float getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public HashMap<Integer, Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(HashMap<Integer, Transaction> transactions) {
		this.transactions = transactions;
	}
	public int getTransactionIdCounter() {
		return transactionIdCounter;
	}
	public void setTransactionIdCounter(int transactionIdCounter) {
		this.transactionIdCounter = transactionIdCounter;
	}
	public int getTransactionIdxCounter() {
		return transactionIdxCounter;
	}
	public void setTransactionIdxCounter(int transactionIdxCounter) {
		this.transactionIdxCounter = transactionIdxCounter;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(accountBalance);
		result = prime * result + accountNo;
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + pinCounter;
		result = prime * result + pinNumber;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + transactionIdCounter;
		result = prime * result + transactionIdxCounter;
		result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (Float.floatToIntBits(accountBalance) != Float.floatToIntBits(other.accountBalance))
			return false;
		if (accountNo != other.accountNo)
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (pinCounter != other.pinCounter)
			return false;
		if (pinNumber != other.pinNumber)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (transactionIdCounter != other.transactionIdCounter)
			return false;
		if (transactionIdxCounter != other.transactionIdxCounter)
			return false;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", pinNumber=" + pinNumber + ", pinCounter=" + pinCounter
				+ ", accountBalance=" + accountBalance + ", accountType=" + accountType + ", status=" + status
				+ ", transactions=" + transactions + ", transactionIdCounter=" + transactionIdCounter
				+ ", transactionIdxCounter=" + transactionIdxCounter + "]";
	}
	public Account(int accountNo, int pinNumber, int pinCounter, float accountBalance, String accountType,
			String status) {
		super();
		this.accountNo = accountNo;
		this.pinNumber = pinNumber;
		this.pinCounter = pinCounter;
		this.accountBalance = accountBalance;
		this.accountType = accountType;
		this.status = status;
	}
	
}

