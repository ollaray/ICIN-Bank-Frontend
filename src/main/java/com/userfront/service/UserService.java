package com.userfront.service;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.springframework.ui.Model;

import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;

public interface UserService {
	User findByUsername(String username);
	User findByEmail(String email);
	boolean checkUserExists(String username, String email);
	boolean checkUsernameExists(String username);
	boolean checkEmailExists(String email);
	void save(User user);
	User createUser(User user, Set<UserRole> userRoles);
	boolean isAuthenticated();
	void generalMethod(Model model, Principal principal, User user);
	void saveUser(User user);
	List<User> findUserList();
	void enableUser(String username);
	void disableUser(String username);
}
