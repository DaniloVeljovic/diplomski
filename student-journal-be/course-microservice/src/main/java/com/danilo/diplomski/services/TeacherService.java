package com.danilo.diplomski.services;

import java.time.LocalDateTime;
import java.util.List;

import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.DTO.TeacherDTO;
import com.danilo.diplomski.models.data.Teacher;

public interface TeacherService {

	TeacherDTO createTeacher(TeacherDTO teacher);

	Teacher findByUserID(String teacherID);

	List<CourseDTO> findAllCoursesForTeacher(String teacherID);

	List<ObligationDTO> findObligationsForDate(String teacherID, LocalDateTime date);

}
