package com.cg.banking.daoservices;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import com.cg.banking.bean.Account;
import com.cg.banking.bean.Address;
import com.cg.banking.bean.Customer;
import com.cg.banking.bean.Transaction;
import com.cg.banking.utilities.BankingUtility;
@Component(value="bankingDAOServices")
public class BankingDAOServicesImpl implements BankingDAOServices {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int insertCustomer(Customer customer) {
		Session session=sessionFactory.openSession();
		org.hibernate.Transaction tx =session.beginTransaction();
		try{
			Integer customerId=(Integer) session.save(customer);
			tx.commit();
			return customerId;
		}catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
			throw e;
		}	
		finally{
			session.close();
		}
	}
	@Override
	public int insertAccount(int customerId, Account account) {
		Session session=sessionFactory.openSession();
		org.hibernate.Transaction tx =session.beginTransaction();
		Long accountNo = null;
		try {
			Customer customer=getCustomer(customerId);
			account.setStatus("Active");
			account.setCustomer(customer);
			accountNo=(Long) session.save(account);
			tx.commit();

		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		finally {
			session.close();
		}
		return customerId;
	}

	@Override
	public boolean updateAccount(int customerId, Account account) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = null;
		try {
			tx = session.beginTransaction(); 
			session.update(account); 
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
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
		Session session=sessionFactory.openSession();
		org.hibernate.Transaction tx =session.beginTransaction();
		Integer transactionId=null;
		try {
			Account account=getAccount(customerId, accountNo);
			transaction.setAccount(account);
			transactionId=(Integer) session.save(transaction);
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
		finally {
			session.close();
		}
		return true;
	}
	@Override
	public boolean deleteCustomer(int customerId) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = null;
		try {
			tx = session.beginTransaction(); 
			Customer customer=(Customer) session.get(Customer.class, customerId);
			session.delete(customer); 
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
		return true;
	}

	@Override
	public boolean deleteAccount(int customerId, long accountNo) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = null;
		try {
			tx = session.beginTransaction(); 
			Account account=getAccount(customerId, accountNo);
			session.delete(account); 
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
		return true;
	}
	@Override
	public Customer getCustomer(int customerId) {
		return (Customer) sessionFactory.openSession().get(Customer.class,customerId);
	}

	@Override
	public Account getAccount(int customerId, long accountNo) {
		Session session=sessionFactory.openSession();
		Account account=(Account) session.get(Account.class, accountNo);
		//	System.out.println(account);
		if(account.getCustomer().getCustomerId()==customerId){
			session.close();
			return account;
		}
		else
			return null;
	}
	@Override
	public List<Customer> getCustomers() {
		return sessionFactory.openSession().createQuery("FROM Customer").list();
	}

	@Override
	public List<Account> getAccounts(int customerId) {
		return sessionFactory.openSession().createQuery("FROM Account").list();
	}

	@Override
	public List<Transaction> getTransactions(int customerId, long accountNo) {
		return sessionFactory.openSession().createQuery("FROM Transaction").list();
	}
}