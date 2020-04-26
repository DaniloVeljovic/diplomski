package com.danilo.diplomski.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.danilo.diplomski.models.DTO.UserDTO;

public interface UserService extends UserDetailsService {
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	//STUDENTDTO
	UserDTO getUserDetailsByEmail(String email);
}
