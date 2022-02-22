package com.userfront.service;

import java.security.Principal;
import java.util.List;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;

public interface TransactionService {
	List<PrimaryTransaction> findPrimaryTransactionList(String username);
	List<SavingsTransaction> findSavingsTransactionList(String username);
	void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);
	void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
	void savePrimaryWithdrawalTransaction(PrimaryTransaction primaryTransaction);
	void saveSavingsWithdrawalTransaction(SavingsTransaction savingsTransaction);
	void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception;
	Recipient saveRecipient(Recipient recipient);
	Recipient findRecipientByName(String recipientName);
	List<Recipient> findRecipientList(Principal principal);
	void deleteRecipientByName(String recipientName);
	void transferToSomeoneElse(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount,
			SavingsAccount savingsAccount);
}