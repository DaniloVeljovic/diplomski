package com.danilo.diplomski.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.danilo.diplomski.models.data.Student;

@Transactional
public interface StudentRepository extends CrudRepository<Student, Integer> {

	Optional<Student> findByUserID(String userID);
	void deleteByUserID(String userID);
}
