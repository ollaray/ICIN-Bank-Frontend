package com.userfront.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/between-accounts", method=RequestMethod.GET)
	public String betweenAccounts(Model model, Principal principal) {
		if(userService.isAuthenticated()) {
			User user = userService.findByUsername(principal.getName());
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			SavingsAccount savingsAccount = user.getSavingsAccount();
			
			model.addAttribute("transferFrom", "");
			model.addAttribute("tranferTo", "");
			model.addAttribute("amount", "");
			model.addAttribute("primaryAccount", primaryAccount);
			model.addAttribute("savingsAccount", savingsAccount);
			model.addAttribute("username",user.getUsername());
			return "between-accounts";
		}
		return "redirect:/";
		
	}
	
	@RequestMapping(value="/between-accounts", method=RequestMethod.POST)
	public String postBetweenAccounts(@ModelAttribute("transferFrom") String transferFrom,
									 @ModelAttribute("transferTo") String transferTo, 
									 @ModelAttribute("amount") String amount,
									 Model model, Principal principal) throws Exception {
		if(!userService.isAuthenticated()) {
			return "redirect:/";
		}
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);
		model.addAttribute("transferFrom", "");
		model.addAttribute("tranferTo", "");
		model.addAttribute("amount", "");
		model.addAttribute("msg", "Transaction Successful");
		userService.generalMethod(model, principal, user);
		return "between-accounts";
		
	}
	
	@RequestMapping(value="/recipient", method=RequestMethod.GET)
	public String recipient(Model model, Principal principal) {
		if(!userService.isAuthenticated()) {
			return "redirect:/";
		}
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		User user = userService.findByUsername(principal.getName());
		Recipient recipient = new Recipient();
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("recipient",recipient);
		userService.generalMethod(model, principal, user);
		return "recipient";
	}
	
	@RequestMapping(value="/recipient/save", method=RequestMethod.POST)
	public String postRecipient(@ModelAttribute("recipient") Recipient recipient, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		recipient.setUser(user);
		transactionService.saveRecipient(recipient);
		model.addAttribute("msg", "Transaction Successful");
		//userService.generalMethod(model, principal, user);
		return "redirect:/transfer/recipient";
	}
	
	@RequestMapping(value="/recipient/edit", method=RequestMethod.GET)
	public String editRecipient(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Recipient recipient = transactionService.findRecipientByName(recipientName);
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("recipient",recipient);
		
		userService.generalMethod(model, principal, user);
		return "recipient";
	}
	
	@RequestMapping(value="/recipient/delete", method=RequestMethod.GET)
	@Transactional
	public String deleteRecipient(@RequestParam(value="recipientName") String recipientName, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		transactionService.deleteRecipientByName(recipientName);
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		
		Recipient recipient = new Recipient();
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("recipient",recipient);
		userService.generalMethod(model, principal, user);
		return "recipient";
	}
	
	@RequestMapping(value="/to-someone-else", method=RequestMethod.GET)
	public String someoneElse(Model model, Principal principal) {
		if(!userService.isAuthenticated()) {
			return "redirect:/";
		}
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		User user = userService.findByUsername(principal.getName());
		
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("accountType", "");
		userService.generalMethod(model, principal, user);
		return "to-someone-else";
	}
	
	@RequestMapping(value="/to-someone-else", method=RequestMethod.POST)
	public String postToSomeoneElse(@ModelAttribute("recipientName") String recipientName, 
			@ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount,
			Model model, Principal principal) {
		
		if(!userService.isAuthenticated()) {
			return "redirect:/";
		}
		List<Recipient> recipientList = transactionService.findRecipientList(principal);
		User user = userService.findByUsername(principal.getName());
		Recipient recipient = transactionService.findRecipientByName(recipientName);
		transactionService.transferToSomeoneElse(recipient, accountType, amount, user.getPrimaryAccount(), user.getSavingsAccount());
		model.addAttribute("recipientList", recipientList);
		model.addAttribute("accountType", "");
		model.addAttribute("msg", "Transaction Successful");
		userService.generalMethod(model, principal, user);
		return "to-someone-else";
	}
	
	
}
