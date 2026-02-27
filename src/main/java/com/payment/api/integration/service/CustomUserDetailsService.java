package com.payment.api.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.payment.api.integration.entity.User;
import com.payment.api.integration.repository.UserRepository;
import com.payment.api.integration.security.CustomUserDetails;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		CustomUserDetails customUserDetail = new CustomUserDetails(user);

		return customUserDetail;
	}

}
