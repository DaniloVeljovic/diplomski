package com.danilo.diplomski.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.danilo.diplomski.models.data.Course;
@Transactional
public interface CourseRepository extends CrudRepository<Course, Integer> {

	Optional<Course> findByCourseID(String courseID);
	void deleteByCourseID(String courseID);
}
