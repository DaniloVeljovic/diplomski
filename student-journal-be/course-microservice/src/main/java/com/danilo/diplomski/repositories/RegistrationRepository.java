package com.danilo.diplomski.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.danilo.diplomski.models.data.junctiontables.Registration;

@Transactional
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

	Optional<Registration> findByStudentIdAndCourses(String studentId, String courseId);
	Iterable<Registration> findByStudentId(Integer id);
}
