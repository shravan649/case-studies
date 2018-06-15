package com.cg.banking.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
@Entity
public class Account {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long accountNo;
@OneToMany(mappedBy="account")
@MapKey(name="accountNo")
public static Map<Integer,Transaction> transactions;
@ManyToOne
private Customer customer;
private int pinNumber,pinCounter;
private String accountType,status;
private float accountBalance;
private int transactionIdCounter=101;
private int transactionIdxCounter=0;

public Account() {}

public Account(int pinNumber, int pinCounter, String accountType, String status, float accountBalance,
long accountNo) {
super();
this.pinNumber = pinNumber;
this.pinCounter = pinCounter;
this.accountType = accountType;
this.status = status;
this.accountBalance = accountBalance;
this.accountNo = accountNo;
}

public Account(int pinNumber, int pinCounter, String accountType, String status, float accountBalance,
long accountNo, int transactionIdCounter, int transactionIdxCounter,
Map<Integer, Transaction> transactions) {
super();
this.pinNumber = pinNumber;
this.pinCounter = pinCounter;
this.accountType = accountType;
this.status = status;
this.accountBalance = accountBalance;
this.accountNo = accountNo;
this.transactionIdCounter = transactionIdCounter;
this.transactionIdxCounter = transactionIdxCounter;
this.transactions = transactions;
}

public Account( float accountBalance,String accountType) {
super();
this.accountType = accountType;
this.accountBalance = accountBalance;
}
public Customer getCustomer() {
return customer;
}
public void setCustomer(Customer customer) {
this.customer = customer;
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

public float getAccountBalance() {
return accountBalance;
}

public void setAccountBalance(float accountBalance) {
this.accountBalance = accountBalance;
}

public long getAccountNo() {
return accountNo;
}

public void setAccountNo(long accountNo) {
this.accountNo = accountNo;
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

public Map<Integer, Transaction> getTransactions() {
return transactions;
}

public void setTransactions(Map<Integer, Transaction> transactions) {
this.transactions = transactions;
}

@Override
public int hashCode() {
final int prime = 31;
int result = 1;
result = prime * result + Float.floatToIntBits(accountBalance);
result = prime * result + (int) (accountNo ^ (accountNo >>> 32));
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
return "Account [pinNumber=" + pinNumber + ", pinCounter=" + pinCounter + ", accountType=" + accountType
+ ", status=" + status + ", accountBalance=" + accountBalance + ", accountNo=" + accountNo
+ ", transactionIdCounter=" + transactionIdCounter + ", transactionIdxCounter=" + transactionIdxCounter
+ ", transactions=" + transactions + "]";
}
}