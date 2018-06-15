package com.cg.banking.daoservices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import com.cg.banking.bean.Account;
import com.cg.banking.bean.Address;
import com.cg.banking.bean.Customer;
import com.cg.banking.bean.Transaction;
import com.cg.banking.utilities.BankingUtility;

@Component(value="daoServices")
public class BankingDAOServicesImpl implements BankingDAOServices {
	@Autowired(required=true)
	private EntityManagerFactory entityManagerFactory;
	@Override
	public int insertCustomer(Customer customer) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(customer);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return customer.getCustomerId();
	}

	@Override
	public int insertAccount(int customerId, Account account) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Customer customer=entityManager.find(Customer.class, customerId);
		account.setCustomer(customer);
		account.setStatus(BankingUtility.ACCOUNT_STATUS_ACTIVE);
		entityManager.persist(account);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return (int) account.getAccountNo();
	}

	@Override
	public boolean updateAccount(int customerId, Account account) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Customer customer=entityManager.find(Customer.class, customerId);
		account.setCustomer(customer);
		entityManager.merge(account);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}

	@Override
	public int generatePin(int customerId, Account account) {
		account.setPinNumber(1111);
		updateAccount(customerId, account);
		return account.getPinNumber();
	}
	@Override
	public boolean insertTransaction(int customerId, long accountNo, Transaction transaction) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Account account=getAccount(customerId, accountNo);
		transaction.setAccount(account);
		entityManager.merge(transaction);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}
	@Override
	public boolean deleteCustomer(int customerId) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Customer customer=entityManager.find(Customer.class, customerId);
		entityManager.remove(customer);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}
	@Override
	public boolean deleteAccount(int customerId, long accountNo) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Account account=entityManager.find(Account.class, accountNo);
		entityManager.remove(account);
		entityManager.flush();
		entityManager.getTransaction().commit();
		entityManager.close();
		return true;
	}
	@Override
	public Customer getCustomer(int customerId) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Customer customer = entityManager.find(Customer.class, customerId);
		entityManager.getTransaction().commit();
		entityManager.close();
		return customer;
	}
	@Override
	public Account getAccount(int customerId, long accountNo) {
		Customer customer=getCustomer(customerId);
		if(customer.getCustomerId()==customerId)
		return entityManagerFactory.createEntityManager().getReference(Account.class, accountNo);
		else
		return null;
	}
	@Override
	public List<Customer> getCustomers() {
		javax.persistence.TypedQuery<Customer> query =
			     entityManagerFactory.createEntityManager().createQuery("FROM Customer", Customer.class);
			  List<Customer> customers = query.getResultList();
		return customers;
	}
	@Override
	public List<Account> getAccounts(int customerId) {
		javax.persistence.TypedQuery<Account> query =
			    entityManagerFactory.createEntityManager().createQuery("FROM Account where customer_customerid=:customerid", Account.class);
		query.setParameter("customerid", customerId);	  
		List<Account> accounts = query.getResultList();
		return accounts;
	}
	@Override
	public List<Transaction> getTransactions(int customerId, long accountNo) {
		javax.persistence.TypedQuery<Transaction> query =
			    entityManagerFactory.createEntityManager().createQuery("FROM Transaction where account_accountno=:accountno", Transaction.class);
		query.setParameter("accountno", accountNo);	  
		List<Transaction> transactions = query.getResultList();
		return transactions;
	}
	
	
}