package com.userfront.service.serviceImpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userfront.dao.UserDao;
import com.userfront.domain.User;

@Service
public class UserSecurityService implements UserDetailsService {
	@Autowired
	private UserDao userDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(null == user) {
			LOG.warn("Username {} not found",username);
			throw new UsernameNotFoundException("Username "+ username+" Not Found");
		}
		return user;
	}
	
}
