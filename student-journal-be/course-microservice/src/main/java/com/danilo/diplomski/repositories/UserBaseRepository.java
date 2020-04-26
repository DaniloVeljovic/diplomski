package com.danilo.diplomski.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danilo.diplomski.models.data.User;

public interface UserBaseRepository<T extends User> extends CrudRepository<T, Integer> {

}
