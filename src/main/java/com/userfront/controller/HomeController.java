package com.userfront.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.dao.RoleDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.UserService;

@Controller
//@CrossOrigin(origins = "http://localhost:4200")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleDao roleDao;
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
//	@RequestMapping("/login")
//	public String login(@ModelAttribute("user")Model model) {
//		
//	}
	
	@RequestMapping(value= "/register", method = RequestMethod.GET)
	public String register(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registration(@ModelAttribute("user") User user, Model model) {
		if(userService.checkUserExists(user.getUsername(),user.getEmail())) {
			if(userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}
			if(userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists",true);
			}
			return "register";
		}else {
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
			userService.createUser(user, userRoles);
			//userService.save(user);
			return "redirect:/";
		}
	}
	
	@RequestMapping("/dashboard")
	public String dashboard(Principal principal, Model model) {
		if(userService.isAuthenticated()) {
			User user = userService.findByUsername(principal.getName()); 
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			SavingsAccount savingsAccount = user.getSavingsAccount();
			model.addAttribute("primaryAccount", primaryAccount);
			model.addAttribute("savingsAccount", savingsAccount);
			model.addAttribute("username",user.getUsername());
			
			return "dashboard";
		}
		return "redirect:/";
		
	}
	
}
