package com.userfront.service.serviceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;


@Service
public class AccountServiceImpl implements AccountService{
	
	private static int nextAccountNumber = 1122003489;
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Override
	public PrimaryAccount createPrimaryAccount() {
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAccountNumber(accountGenerator());
		
		primaryAccountDao.save(primaryAccount);
		
		return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
	}

	@Override
	public SavingsAccount createSavingsAccount() {
		
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGenerator());
		
		savingsAccountDao.save(savingsAccount);
		
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}
	
	public void deposit() {
		
	}

	private int accountGenerator() {
		return ++nextAccountNumber;
	}

	@Override
	public void deposit(String accountType, double amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		if(accountType.equalsIgnoreCase("Primary")) {
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			Date date = new Date();
			PrimaryTransaction primaryTransaction = 
					new PrimaryTransaction(date, "Credit Transaction on Primary Account","Account","Finished",amount, primaryAccount.getAccountBalance(), primaryAccount);
			primaryAccountDao.save(primaryAccount);
			transactionService.savePrimaryDepositTransaction(primaryTransaction);
			
		}else if(accountType.equalsIgnoreCase("Savings")) {
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			SavingsTransaction savingsTransaction = 
					new SavingsTransaction(date, "Credit Transaction on Savings Account","Account","Finished",amount, savingsAccount.getAccountBalance(), savingsAccount);
			savingsAccountDao.save(savingsAccount);
			transactionService.saveSavingsDepositTransaction(savingsTransaction);
		}
		
	}
	

	
	@Override
	public void withdraw(String accountType, double amount, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		
		if(accountType.equalsIgnoreCase("Primary")) {
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			Date date = new Date();
			PrimaryTransaction primaryTransaction = 
					new PrimaryTransaction(date, "Debit Transaction on Primary Account","Account","Finished",amount, primaryAccount.getAccountBalance(), primaryAccount);
			primaryAccountDao.save(primaryAccount);
			transactionService.savePrimaryWithdrawalTransaction(primaryTransaction);
			
		}else if(accountType.equalsIgnoreCase("Savings")) {
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			SavingsTransaction savingsTransaction = 
					new SavingsTransaction(date, "Debit Transaction on  Savings Account","Account","Finished",amount, savingsAccount.getAccountBalance(), savingsAccount);
			savingsAccountDao.save(savingsAccount);
			transactionService.saveSavingsWithdrawalTransaction(savingsTransaction);
		}
		
	}
	
	
}
