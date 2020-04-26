package com.danilo.diplomski.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import com.danilo.diplomski.models.data.User;
@Transactional
public interface UserRepository extends UserBaseRepository<User>{

	Optional<User> findByEmail(String email);
	
}
