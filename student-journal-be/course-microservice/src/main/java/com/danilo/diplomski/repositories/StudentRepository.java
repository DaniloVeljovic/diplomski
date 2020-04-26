package com.danilo.diplomski.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import com.danilo.diplomski.models.data.Student;

@Transactional
public interface StudentRepository extends UserBaseRepository<Student> {

	Optional<Student> findByUserID(String userID);
	
}
