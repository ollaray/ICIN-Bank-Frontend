package com.userfront.service.serviceImpl;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.userfront.dao.RoleDao;
import com.userfront.dao.UserDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

import jdk.internal.org.jline.utils.Log;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AccountService accountService;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	public void save(User user) {
		userDao.save(user);
	}
	
	public User createUser(User user, Set<UserRole> userRoles) {
		User usr = userDao.findByUsername(user.getUsername());
		if(usr != null) {
			LOG.info("User with the username {} already exists", user.getUsername());
		}else {
			String encPwd = passwordEncoder.encode(user.getPassword());
			user.setPassword(encPwd);
			for(UserRole ur: userRoles) {
				roleDao.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			user.setPrimaryAccount(accountService.createPrimaryAccount());
			user.setSavingsAccount(accountService.createSavingsAccount());
			
			usr = userDao.save(user);
		}
		return usr;
	}
	
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	public boolean checkUserExists(String username, String email) {
		if(checkUsernameExists(username) || checkEmailExists(email)) {
			return true;
		}
			return false;
	}
	
	public boolean checkUsernameExists(String username) {
		if(null != findByUsername(username)) {
			return true;
		}
			return false;
	}
	
	public boolean checkEmailExists(String email) {
		if(null != findByEmail(email)) {
			return true;
		}
			return false;
	}
	
	public void saveUser(User user) {
		userDao.save(user);
	}
	
	@Override
	public boolean isAuthenticated() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null || AnonymousAuthenticationToken.class.isAssignableFrom(auth.getClass())) {
			return false;
		}
			
		return auth.isAuthenticated();
	}
	public void generalMethod(Model model, Principal principal, User user) {
		user = findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		model.addAttribute("primaryAccount", primaryAccount);
		model.addAttribute("savingsAccount", savingsAccount);
		model.addAttribute("username",user.getUsername());
	}

	@Override
	public List<User> findUserList() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	@Override
	public void enableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(true);
		userDao.save(user);
		
	}

	@Override
	public void disableUser(String username) {
		User user = findByUsername(username);
		user.setEnabled(false);
		userDao.save(user);
		
	}
	

	
}
