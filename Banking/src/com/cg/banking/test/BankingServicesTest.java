package com.cg.banking.test;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.cg.banking.bean.Account;
import com.cg.banking.bean.Address;
import com.cg.banking.bean.Customer;
import com.cg.banking.bean.Transaction;
import com.cg.banking.daoservices.BankingDAOServicesImpl;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.exceptions.CustomerNotFoundException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.services.BankingServices;
import com.cg.banking.services.BankingServicesImpl;
import com.cg.banking.utilities.BankingUtility;
public class BankingServicesTest {
	private static BankingServices bankingServices;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		bankingServices=new BankingServicesImpl();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		bankingServices=null;
	}
	@Before
	public void setUp() throws Exception {
	Customer customer1=new Customer(1, 86898949, 12232331, "ss", "k", "ss@gmail.com", "ASAJSX", "may20", new Address(534342, "vskp", "ap"), new Address(231421, "vskp", "ap"));
	Customer customer2=new Customer(2, 9289229, 1342323, "k", "ss", "k@gmail.com", "ASDASX", "may20", new Address(534342, "pun", "mh"), new Address(231421, "vskp", "ap"));
	BankingDAOServicesImpl.customers.put(BankingUtility.CUSTOMER_ID_COUNTER++, customer1);
	BankingDAOServicesImpl.customers.put(BankingUtility.CUSTOMER_ID_COUNTER++, customer2);
	Account account1=new Account(1111, 1000, 1, 1000, "sav", BankingUtility.ACCOUNT_STATUS_ACTIVE);
	Account account2=new Account(1112, 10000, 1, 2000, "cur", BankingUtility.ACCOUNT_STATUS_BLOCKED);
	BankingDAOServicesImpl.customers.get(customer1.getCustomerID()).getAccounts().put(BankingUtility.ACCOUNT_NO_COUNTER++, account1);
	BankingDAOServicesImpl.customers.get(customer2.getCustomerID()).getAccounts().put(BankingUtility.ACCOUNT_NO_COUNTER++, account2);
	Transaction transaction1=new Transaction(101101, 5000, "net");
	Transaction transaction2=new Transaction(101102, 3000, "offline");
	BankingDAOServicesImpl.customers.get(customer1.getCustomerID()).getAccounts().get(account1.getAccountNo()).getTransactions().put(transaction1.getTransactionId(), transaction1);
	BankingDAOServicesImpl.customers.get(customer2.getCustomerID()).getAccounts().get(account2.getAccountNo()).getTransactions().put(transaction2.getTransactionId(), transaction2);	
	}
	@After
	public void tearDown() throws Exception {
	BankingDAOServicesImpl.customers.clear();
	BankingUtility.CUSTOMER_ID_COUNTER=1;
	BankingUtility.ACCOUNT_NO_COUNTER=1111;
	}
	@Test
	public void testAcceptCustomerDetailsForValidData()throws BankingServicesDownException{
		int expectedId=3;
		int actualId=bankingServices.acceptCustomerDetails("ss", "k", "s@gmail.com", "ASAASDX", "vizag", "ap", 534342, "vizag", "ap", 231421);
		Assert.assertEquals(expectedId, actualId);
	}
	@Test(expected=CustomerNotFoundException.class)
	public void testOpenAccountForInvalidCustomerId()throws CustomerNotFoundException,BankingServicesDownException,
	InvalidAmountException,InvalidAccountTypeException{
		bankingServices.openAccount(3, "sav", 1000);
	}
	@Test(expected=InvalidAccountTypeException.class)
	public void testOpenAccountForInvalidAccountType()throws CustomerNotFoundException,BankingServicesDownException,
	InvalidAccountTypeException,InvalidAmountException{
		bankingServices.openAccount(2, "hgzj", 1000);
	}
	@Test(expected=InvalidAmountException.class)
	public void testOpenAccountForInvalidAmount()throws CustomerNotFoundException,BankingServicesDownException,
	InvalidAccountTypeException,InvalidAmountException{
		bankingServices.openAccount(2, "cur", -300);
	}
	
}
