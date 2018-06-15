package com.cg.banking.services;
import java.util.List;
import com.cg.banking.bean.Account;
import com.cg.banking.bean.Address;
import com.cg.banking.bean.Customer;
import com.cg.banking.bean.Transaction;
import com.cg.banking.daoservices.BankingDAOServices;
import com.cg.banking.daoservices.BankingDAOServicesImpl;
import com.cg.banking.exceptions.AccountBlockedException;
import com.cg.banking.exceptions.AccountNotFoundException;
import com.cg.banking.exceptions.BankingServicesDownException;
import com.cg.banking.exceptions.CustomerNotFoundException;
import com.cg.banking.exceptions.InsufficientAmountException;
import com.cg.banking.exceptions.InvalidAccountTypeException;
import com.cg.banking.exceptions.InvalidAmountException;
import com.cg.banking.exceptions.InvalidPinNumberException;
import com.cg.banking.utilities.BankingUtility;
@Component(value="bankingServices")
public class BankingServicesImpl implements BankingServices {
	private BankingDAOServices daoServices=new BankingDAOServicesImpl();
	@Override
	public int acceptCustomerDetails(String firstName, String lastName, String customerEmailId, String panCard,
			String localAddressCity, String localAddressState, int localAddressPinCode, String homeAddressCity,
			String homeAddressState, int homeAddressPinCode) throws BankingServicesDownException {
		return daoServices.insertCustomer(new Customer(firstName, lastName, customerEmailId, panCard, new Address(localAddressPinCode,  localAddressCity,localAddressState),
				new Address(homeAddressPinCode, homeAddressCity, homeAddressState)));
	}
	@Override
	public long openAccount(int customerId, String accountType, float initBalance) throws InvalidAmountException,
	CustomerNotFoundException, InvalidAccountTypeException, BankingServicesDownException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(initBalance<=0)
			throw new InvalidAmountException("Initial balance should not be less than 1");
		if(!accountType.equals("Savings")&&!accountType.equals("savings")&&!accountType.equals("SAVINGS")&&
				!accountType.equals("Current")&&!accountType.equals("current")&&!accountType.equals("CURRENT")&&
				!accountType.equals("Salary")&&!accountType.equals("salary")&&!accountType.equals("SALARY"))
			throw new InvalidAccountTypeException("Account Type : "+accountType+" is not valid.");
		return daoServices.insertAccount(customerId, new Account(initBalance, accountType));
	}
	@Override
	public float depositAmount(int customerId, long accountNo, float amount) throws CustomerNotFoundException,
	AccountNotFoundException, BankingServicesDownException, AccountBlockedException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		if(getAccountDetails(customerId, accountNo).getStatus()!=BankingUtility.ACCOUNT_STATUS_ACTIVE)
			throw new AccountBlockedException("AccountNo "+accountNo+" is Blocked");
		daoServices.insertTransaction(customerId, accountNo, new Transaction(amount, "Deposit"));
		this.getAccountDetails(customerId, accountNo).setAccountBalance(getAccountDetails(customerId, accountNo).getAccountBalance()+amount);
		return this.getAccountDetails(customerId, accountNo).getAccountBalance();
	}

	@Override
	public float withdrawAmount(int customerId, long accountNo, float amount, int pinNumber)
			throws InsufficientAmountException, CustomerNotFoundException, AccountNotFoundException,
			InvalidPinNumberException, BankingServicesDownException, AccountBlockedException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		if(getAccountDetails(customerId, accountNo).getStatus()!=BankingUtility.ACCOUNT_STATUS_ACTIVE)
			throw new AccountBlockedException("AccountNo "+accountNo+" is Blocked");
		if(getAccountDetails(customerId, accountNo).getPinNumber()!=pinNumber){
			getAccountDetails(customerId, accountNo).setPinCounter(getAccountDetails(customerId, accountNo).getPinCounter()+1);
			if(getAccountDetails(customerId, accountNo).getPinCounter()>2)
				getAccountDetails(customerId, accountNo).setStatus(BankingUtility.ACCOUNT_STATUS_BLOCKED);
			throw new InvalidPinNumberException("Invalid PIN Number.");
		}
		if(amount>getAccountDetails(customerId, accountNo).getAccountBalance())
			throw new InsufficientAmountException("Insufficient Amount");
		while(getAccountDetails(customerId, accountNo)!=null&&getAccountDetails(customerId, accountNo).getPinCounter()<3)
			if(pinNumber==getAccountDetails(customerId, accountNo).getPinNumber())
				if(amount<=getAccountDetails(customerId, accountNo).getAccountBalance()) {
					daoServices.insertTransaction(customerId, accountNo, new Transaction(amount, "Withdrawal"));
					this.getAccountDetails(customerId, accountNo).setAccountBalance(getAccountDetails(customerId, accountNo).getAccountBalance()-amount);
					return this.getAccountDetails(customerId, accountNo).getAccountBalance();
				}
		return 0;
	}

	@Override
	public boolean fundTransfer(int customerIdTo, long accountNoTo, int customerIdFrom, long accountNoFrom,
			float transferAmount, int pinNumber) throws InsufficientAmountException, CustomerNotFoundException,
	AccountNotFoundException, InvalidPinNumberException, BankingServicesDownException, AccountBlockedException {
		if(getCustomerDetails(customerIdTo)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerIdTo+" not found.");
		if(getAccountDetails(customerIdTo, accountNoTo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNoTo+" not found.");
		if(getAccountDetails(customerIdTo, accountNoTo).getStatus()!=BankingUtility.ACCOUNT_STATUS_ACTIVE)
			throw new AccountBlockedException("AccountNo "+accountNoTo+" is Blocked");
		if(getCustomerDetails(customerIdFrom)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerIdFrom+" not found.");
		if(getAccountDetails(customerIdFrom, accountNoFrom)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNoFrom+" not found.");
		if(getAccountDetails(customerIdFrom, accountNoFrom).getStatus()!=BankingUtility.ACCOUNT_STATUS_ACTIVE)
			throw new AccountBlockedException("AccountNo "+accountNoFrom+" is Blocked");
		if(getAccountDetails(customerIdFrom, accountNoFrom).getPinNumber()!=pinNumber){
			getAccountDetails(customerIdFrom, accountNoFrom).setPinCounter(getAccountDetails(customerIdFrom, accountNoFrom).getPinCounter()+1);
			if(getAccountDetails(customerIdFrom, accountNoFrom).getPinCounter()>2)
				getAccountDetails(customerIdFrom, accountNoFrom).setStatus(BankingUtility.ACCOUNT_STATUS_BLOCKED);
			throw new InvalidPinNumberException("Invalid PIN Number.");
		}
		if(pinNumber==getAccountDetails(customerIdFrom, accountNoFrom).getPinNumber()) {
			if(transferAmount<=getAccountDetails(customerIdFrom, accountNoFrom).getAccountBalance()) {
				float totalBalanceTo=daoServices.getAccount(customerIdTo, accountNoTo).getAccountBalance()+transferAmount;
				daoServices.getAccount(customerIdTo, accountNoTo).setAccountBalance(totalBalanceTo);
				float totalBalanceFrom=daoServices.getAccount(customerIdFrom, accountNoFrom).getAccountBalance()-transferAmount;
				daoServices.getAccount(customerIdFrom, accountNoFrom).setAccountBalance(totalBalanceFrom);
				daoServices.insertTransaction(customerIdTo, accountNoTo, new Transaction(totalBalanceTo, "Deposit"));
				daoServices.insertTransaction(customerIdFrom, accountNoFrom, new Transaction(totalBalanceFrom, "Withdrawal"));
				return true;
			}
		}
		return false;
	}

	@Override
	public Customer getCustomerDetails(int customerId) throws CustomerNotFoundException, BankingServicesDownException {
		if(daoServices.getCustomer(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		return daoServices.getCustomer(customerId);
	}

	@Override
	public Account getAccountDetails(int customerId, long accountNo)
			throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(daoServices.getAccount(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		return daoServices.getAccount(customerId, accountNo);
	}

	@Override
	public int generateNewPin(int customerId, long accountNo)
			throws CustomerNotFoundException, AccountNotFoundException, BankingServicesDownException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		return daoServices.generatePin(customerId, this.getAccountDetails(customerId, accountNo));
	}

	@Override
	public boolean changeAccountPin(int customerId, long accountNo, int oldPinNumber, int newPinNumber)
			throws CustomerNotFoundException, AccountNotFoundException, InvalidPinNumberException,
			BankingServicesDownException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		if(getAccountDetails(customerId, accountNo).getPinNumber()!=oldPinNumber){
			getAccountDetails(customerId, accountNo).setPinCounter(getAccountDetails(customerId, accountNo).getPinCounter()+1);
			if(getAccountDetails(customerId, accountNo).getPinCounter()>2)
				getAccountDetails(customerId, accountNo).setStatus(BankingUtility.ACCOUNT_STATUS_BLOCKED);
			throw new InvalidPinNumberException("Invalid PIN Number.");
		}
		if(oldPinNumber==getAccountDetails(customerId, accountNo).getPinNumber()) {
			oldPinNumber=newPinNumber;
			getAccountDetails(customerId, accountNo).setPinNumber(oldPinNumber);
			return true;
		}
		return false;
	}

	@Override
	public List<Customer> getAllCustomerDetails() throws BankingServicesDownException {
		return daoServices.getCustomers();
	}

	@Override
	public List<Account> getcustomerAllAccountDetails(int customerId)
			throws BankingServicesDownException, CustomerNotFoundException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		return daoServices.getAccounts(customerId);
	}

	@Override
	public List<Transaction> getAccountAllTransaction(int customerId, long accountNo)
			throws BankingServicesDownException, CustomerNotFoundException, AccountNotFoundException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		return daoServices.getTransactions(customerId, accountNo);
	}

	@Override
	public String accountStatus(int customerId, long accountNo) throws BankingServicesDownException,
	CustomerNotFoundException, AccountNotFoundException, AccountBlockedException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		if(getAccountDetails(customerId, accountNo).getStatus()!=BankingUtility.ACCOUNT_STATUS_ACTIVE)
			throw new AccountBlockedException("AccountNo "+accountNo+" is Blocked");
		return getAccountDetails(customerId, accountNo).getStatus();
	}

	@Override
	public boolean closeAccount(int customerId, long accountNo)
			throws BankingServicesDownException, CustomerNotFoundException, AccountNotFoundException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		if(getAccountDetails(customerId, accountNo).getAccountBalance()==0) {
			daoServices.deleteAccount(customerId, accountNo);
			return true;
		}
		return false;
	}
	@Override
	public float showBalance(int customerId, long accountNo, int pinNumber) throws CustomerNotFoundException,
	AccountNotFoundException, BankingServicesDownException, AccountBlockedException {
		if(getCustomerDetails(customerId)==null)
			throw new CustomerNotFoundException("CustomerID : "+customerId+" not found.");
		if(getAccountDetails(customerId, accountNo)==null)
			throw new AccountNotFoundException("AccountNo : "+accountNo+" not found.");
		if(getAccountDetails(customerId, accountNo).getStatus()!=BankingUtility.ACCOUNT_STATUS_ACTIVE)
			throw new AccountBlockedException("AccountNo "+accountNo+" is Blocked");
		if(customerId==daoServices.getCustomer(customerId).getCustomerID()
				&&accountNo==daoServices.getAccount(customerId, accountNo).getAccountNo()
				&&pinNumber==daoServices.getAccount(customerId, accountNo).getPinNumber())
			return daoServices.getAccount(customerId, accountNo).getAccountBalance();
		return 0;
	}
}