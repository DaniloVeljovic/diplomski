package com.danilo.diplomski.services;

import java.time.LocalDateTime;
import java.util.List;

import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.DTO.RegistrationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.data.Student;

public interface StudentService {

	StudentDTO createStudent(StudentDTO studentToCreate);

	List<CourseDTO> findAllCoursesForStudent(String studentID);

	List<ObligationDTO> findAllObligationsForDate(String studentID, LocalDateTime date);

	Student findByUserID(String studentID);

	void deleteRegistration(RegistrationDTO resFail);

	void confirmRegistration(RegistrationDTO resSuccess);

	void checkIfEnroled(String userID, String courseID);

}
