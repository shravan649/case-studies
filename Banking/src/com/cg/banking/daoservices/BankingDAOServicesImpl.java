package com.cg.banking.daoservices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.cg.banking.bean.Account;
import com.cg.banking.bean.Address;
import com.cg.banking.bean.Customer;
import com.cg.banking.bean.Transaction;
import com.cg.banking.utilities.BankingUtility;
@Component(value="bankingDAOServices")
public class BankingDAOServicesImpl implements BankingDAOServices {
public static	HashMap<Integer, Customer>customers=new HashMap<>();
	@Override
	public int insertCustomer(Customer customer) {
		customer.setCustomerID(BankingUtility.CUSTOMER_ID_COUNTER++);
		customers.put(customer.getCustomerID(), customer);
		return customer.getCustomerID();
	}
	@Override
	public int insertAccount(int customerId, Account account) {
		customers.get(customerId).getAccounts().put(BankingUtility.ACCOUNT_NO_COUNTER, account);
		account.setAccountNo(BankingUtility.ACCOUNT_NO_COUNTER++);
		account.setStatus("Active");
		return account.getAccountNo();
	}
	@Override
	public boolean updateAccount(int customerId, Account account) {
		if(customers.get(customerId).getAccounts().containsKey(account.getAccountNo())==true){
			customers.get(customerId).getAccounts().put(account.getAccountNo(),account);
			return true;
		}		
		return false;
	}
	@Override
	public int generatePin(int customerId, Account account) {
		Random generatePin=new Random();
		int pinNum=generatePin.nextInt(9999) + 1000;
		customers.get(customerId).getAccounts().get(account.getAccountNo()).setPinNumber(pinNum);
		return pinNum;
	}
	@Override
	public boolean insertTransaction(int customerId, long accountNo, Transaction transaction) {
		if(getAccount(customerId, accountNo)==null)
			return false;
		int id=getAccount(customerId, accountNo).getTransactionIdCounter();
		customers.get(customerId).getAccounts().get(accountNo).getTransactions().put(getAccount(customerId, accountNo).getTransactionIdxCounter(), transaction);
		customers.get(customerId).getAccounts().get(accountNo).getTransactions().get(getAccount(customerId, accountNo).getTransactionIdxCounter()).setTransactionId(id++);
		return true;
	}
	@Override
	public boolean deleteCustomer(int customerId) {
		customers.remove(customerId);
		return true;
	}
	@Override
	public boolean deleteAccount(int customerId, long accountNo) {
		customers.get(customerId).getAccounts().remove(accountNo);
		return true;
	}
	@Override
	public Customer getCustomer(int customerId) {
		return customers.get(customerId);
	}
	@Override
	public Account getAccount(int customerId, long accountNo) {
		return customers.get(customerId).getAccounts().get(accountNo);		
	}
	@Override
	public List<Customer> getCustomers() {
		return new ArrayList<Customer>(customers.values());
	}
	@Override
	public List<Account> getAccounts(int customerId) {
		List<Account> accountList=new ArrayList<Account>(customers.get(customerId).getAccounts().values());
		return accountList;
	}
	@Override
	public List<Transaction> getTransactions(int customerId, long accountNo) {
		List<Transaction> transactionList=new ArrayList<Transaction>(customers.get(customerId).getAccounts().get(accountNo).getTransactions().values());
		return transactionList;
	}
}