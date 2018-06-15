package com.cg.banking.bean;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Column;
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerId;
	private String firstName,lastName,emailId,panCard;
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="city",column=@Column(name="localCity")),
		@AttributeOverride(name="state",column=@Column(name="localState")),
		@AttributeOverride(name="pincode",column=@Column(name="localPincode"))})
	private Address localAddress;
	@Embedded
	private Address homeAddress; 
	@Embedded
	@OneToMany(mappedBy="customer")
	public static Map<Long,Account> accounts;
	private int accountIdxCounter=0;

	public Customer(){}
	public Customer(int customerId, String firstName, String lastName, String emailId, String panCard,
			Address localAddress, Address homeAddress,  Map<Long, Account> accounts) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.panCard = panCard;
		this.localAddress = localAddress;
		this.homeAddress = homeAddress;
		this.accounts = accounts;
	}
	public Customer(String firstName, String lastName, String emailId, String panCard, Address localAddress,
			Address homeAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.panCard = panCard;
		this.localAddress = localAddress;
		this.homeAddress = homeAddress;
	}
	public Customer(int customerId, String firstName, String lastName, String emailId, String panCard,
			Address localAddress, Address homeAddress) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.panCard = panCard;
		this.localAddress = localAddress;
		this.homeAddress = homeAddress;
	}

	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPanCard() {
		return panCard;
	}
	public void setPanCard(String panCard) {
		this.panCard = panCard;
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
	public int getAccountIdxCounter() {
		return accountIdxCounter;
	}
	public void setAccountIdxCounter(int accountIdxCounter) {
		this.accountIdxCounter = accountIdxCounter;
	}
	public Map<Long, Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(Map<Long, Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountIdxCounter;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + customerId;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((homeAddress == null) ? 0 : homeAddress.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((localAddress == null) ? 0 : localAddress.hashCode());
		result = prime * result + ((panCard == null) ? 0 : panCard.hashCode());
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
		if (accountIdxCounter != other.accountIdxCounter)
			return false;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (customerId != other.customerId)
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
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
		if (panCard == null) {
			if (other.panCard != null)
				return false;
		} else if (!panCard.equals(other.panCard))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", panCard=" + panCard + ", localAddress=" + localAddress + ", homeAddress="
				+ homeAddress + ", accountIdxCounter=" + accountIdxCounter + ", accounts=" + accounts + "]";
	}
}