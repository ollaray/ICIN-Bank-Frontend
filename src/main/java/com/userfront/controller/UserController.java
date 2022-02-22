package com.userfront.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.User;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("username",user.getUsername());
		return "profile";
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.POST)
	public String postProfile(@ModelAttribute("user") User usr, Model model) {
		User user = userService.findByUsername(usr.getUsername());
		user.setUsername(usr.getUsername());
		user.setFirstName(usr.getFirstName());
		user.setLastName(usr.getLastName());
		user.setPhone(usr.getPhone());
		user.setEmail(usr.getEmail());
		model.addAttribute("msg", "Profile Information Updated!");
		model.addAttribute("user", user);
		model.addAttribute("username",user.getUsername());
		userService.saveUser(user);
		return "profile";
	}
}
