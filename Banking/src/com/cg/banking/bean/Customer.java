package com.cg.banking.bean;

import java.util.HashMap;

public class Customer {
	private int customerID;
	private long mobileNo, aadharNo;
	private String firstName, lastName, emailID, pancardNo, dateOfBirth;
	private Address localAddress, homeAddress;
	private HashMap<Integer, Account> accounts=new HashMap<>();
	private int accountIdxCounter=0;
	public Customer(){}
	public Customer(int customerID, long mobileNo, long aadharNo, String firstName, String lastName, String emailID,
			String pancardNo, String dateOfBirth, Address localAddress, Address homeAddress,
			HashMap<Integer, Account> accounts, int accountIdxCounter) {
		super();
		this.customerID = customerID;
		this.mobileNo = mobileNo;
		this.aadharNo = aadharNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailID = emailID;
		this.pancardNo = pancardNo;
		this.dateOfBirth = dateOfBirth;
		this.localAddress = localAddress;
		this.homeAddress = homeAddress;
		this.accounts = accounts;
		this.accountIdxCounter = accountIdxCounter;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public long getAadharNo() {
		return aadharNo;
	}
	public void setAadharNo(long aadharNo) {
		this.aadharNo = aadharNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getPancardNo() {
		return pancardNo;
	}
	public void setPancardNo(String pancardNo) {
		this.pancardNo = pancardNo;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Address getLocalAddress() {
		return localAddress;
	}
	public void setLocalAddress(Address localAddress) {
		this.localAddress = localAddress;
	}
	public Address getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}
	public HashMap<Integer, Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(HashMap<Integer, Account> accounts) {
		this.accounts = accounts;
	}
	public int getAccountIdxCounter() {
		return accountIdxCounter;
	}
	public void setAccountIdxCounter(int accountIdxCounter) {
		this.accountIdxCounter = accountIdxCounter;
	}
	public Customer(String firstName, String lastName, String emailID, String pancardNo, Address localAddress,
			Address homeAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailID = emailID;
		this.pancardNo = pancardNo;
		this.localAddress = localAddress;
		this.homeAddress = homeAddress;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (aadharNo ^ (aadharNo >>> 32));
		result = prime * result + accountIdxCounter;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + customerID;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((emailID == null) ? 0 : emailID.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((homeAddress == null) ? 0 : homeAddress.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((localAddress == null) ? 0 : localAddress.hashCode());
		result = prime * result + (int) (mobileNo ^ (mobileNo >>> 32));
		result = prime * result + ((pancardNo == null) ? 0 : pancardNo.hashCode());
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
		Customer other = (Customer) obj;
		if (aadharNo != other.aadharNo)
			return false;
		if (accountIdxCounter != other.accountIdxCounter)
			return false;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (customerID != other.customerID)
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (emailID == null) {
			if (other.emailID != null)
				return false;
		} else if (!emailID.equals(other.emailID))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (homeAddress == null) {
			if (other.homeAddress != null)
				return false;
		} else if (!homeAddress.equals(other.homeAddress))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (localAddress == null) {
			if (other.localAddress != null)
				return false;
		} else if (!localAddress.equals(other.localAddress))
			return false;
		if (mobileNo != other.mobileNo)
			return false;
		if (pancardNo == null) {
			if (other.pancardNo != null)
				return false;
		} else if (!pancardNo.equals(other.pancardNo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", mobileNo=" + mobileNo + ", aadharNo=" + aadharNo
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", emailID=" + emailID + ", pancardNo="
				+ pancardNo + ", dateOfBirth=" + dateOfBirth + ", localAddress=" + localAddress + ", homeAddress="
				+ homeAddress + ", accounts=" + accounts + ", accountIdxCounter=" + accountIdxCounter + "]";
	}
	public Customer(int customerID, long mobileNo, long aadharNo, String firstName, String lastName, String emailID,
			String pancardNo, String dateOfBirth, Address localAddress, Address homeAddress) {
		super();
		this.customerID = customerID;
		this.mobileNo = mobileNo;
		this.aadharNo = aadharNo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailID = emailID;
		this.pancardNo = pancardNo;
		this.dateOfBirth = dateOfBirth;
		this.localAddress = localAddress;
		this.homeAddress = homeAddress;
	}	
}
