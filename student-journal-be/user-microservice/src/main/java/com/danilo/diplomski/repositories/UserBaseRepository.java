package com.danilo.diplomski.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.NoRepositoryBean;

import com.danilo.diplomski.models.data.User;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Integer> {

	Optional<T> findByUserID(String id);
	
	void deleteByUserID(String id);
	
	Optional<T> findByEmail(String email);
	
}
