package com.danilo.diplomski.services;

import java.util.List;

import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;


public interface CourseService {

	CourseDTO createCourse(CourseDTO courseToCreate, String teacherID) throws IllegalArgumentException;

	List<CourseDTO> findAllCourses();

	CourseDTO findCourseByID(String courseID);

	CourseDTO updateCourse(CourseDTO courseToUpdate);

	void deleteByCourseID(String courseID);

	ObligationDTO addObligationToCourse(ObligationDTO obligationToAdd, String courseID);

	CourseDTO enrolStudent(String courseID, String student);

	List<ObligationDTO> getObligationsForCourse(String courseID);

	List<StudentDTO> getStudentsForCourse(String courseID);

}
