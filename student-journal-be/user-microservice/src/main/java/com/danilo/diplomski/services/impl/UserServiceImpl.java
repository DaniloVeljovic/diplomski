package com.danilo.diplomski.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.danilo.diplomski.models.DTO.UserDTO;
import com.danilo.diplomski.repositories.UserRepository;
import com.danilo.diplomski.services.UserService;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<com.danilo.diplomski.models.data.User> foundUser = userRepo.findByEmail(username);

		if (!foundUser.isPresent())
			throw new UsernameNotFoundException(username);

		com.danilo.diplomski.models.data.User user = foundUser.get();

		return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());

	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		ModelMapper modelMapper = new ModelMapper();

		Optional<com.danilo.diplomski.models.data.User> foundUser = userRepo.findByEmail(email);

		if (!foundUser.isPresent())
			throw new UsernameNotFoundException(email);

		UserDTO user = modelMapper.map(foundUser.get(), UserDTO.class);

		return user;
	}

}
