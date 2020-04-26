package com.danilo.diplomski.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.danilo.diplomski.models.data.Notebook;
import com.danilo.diplomski.models.data.Student;

@Transactional
public interface NotebookRepository extends CrudRepository<Notebook, Integer> {
	
	Optional<Notebook> findByCourse(String course);
	Optional<Notebook> findByCourseAndStudentId(String course, Integer studentId);
	void deleteByCourseAndStudent(String course, Student studentId);
}
