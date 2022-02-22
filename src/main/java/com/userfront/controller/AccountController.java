package com.userfront.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.AccountService;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/primary-account")
	public String primaryAccount(Model model, Principal principal){
		if(userService.isAuthenticated()) {
			User user = userService.findByUsername(principal.getName());
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());
			model.addAttribute("primaryTransactionList",primaryTransactionList);
			model.addAttribute("username",user.getUsername());
			model.addAttribute("primaryAccount", primaryAccount);
			
			SavingsAccount savingsAccount = user.getSavingsAccount();
			model.addAttribute("savingsAccount", savingsAccount);
			
			return "primary-account";
		}
		return "redirect:/";
	}
	
	@RequestMapping("/savings-account")
	public String savingsAccount(Model model, Principal principal){
		if(userService.isAuthenticated()) {
			User user = userService.findByUsername(principal.getName());
			List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName()) ;
			SavingsAccount savingsAccount = user.getSavingsAccount();
			model.addAttribute("savingsTransactionList",savingsTransactionList);
			model.addAttribute("username",user.getUsername());
			model.addAttribute("savingsAccount", savingsAccount);
			
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			model.addAttribute("primaryAccount", primaryAccount);
			
			return "savings-account";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/deposit", method = RequestMethod.GET)
	public String deposit(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if(userService.isAuthenticated()) {
			model.addAttribute("username",user.getUsername());
			model.addAttribute("accountType", "");
			model.addAttribute("account", "");
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			SavingsAccount savingsAccount = user.getSavingsAccount();
			model.addAttribute("primaryAccount", primaryAccount);
			model.addAttribute("savingsAccount", savingsAccount);
			return "deposit";
		}
		return "redirect:/";	
		
	}
	
	@RequestMapping(value="/deposit", method = RequestMethod.POST)
	public String depositPost(@ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Model model, Principal principal) {
		accountService.deposit(accountType, Double.parseDouble(amount), principal);
		model.addAttribute("msg", "Deposit Transaction Successful");
		return "deposit";
	}
	
	@RequestMapping(value="/withdraw", method = RequestMethod.GET)
	public String withdraw(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if(userService.isAuthenticated()) {
			model.addAttribute("username",user.getUsername());
			model.addAttribute("accountType", "");
			model.addAttribute("account", "");
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			SavingsAccount savingsAccount = user.getSavingsAccount();
			model.addAttribute("primaryAccount", primaryAccount);
			model.addAttribute("savingsAccount", savingsAccount);
			return "withdrawal";
		}
		return "redirect:/";	
		
	}
	
	@RequestMapping(value="/withdraw", method = RequestMethod.POST)
	public String withdrawPost(@ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Model model, Principal principal) {
		accountService.withdraw(accountType, Double.parseDouble(amount), principal);
		model.addAttribute("msg", "Transaction Successful");
		return "withdrawal";
	}
	
}
