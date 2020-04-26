package com.danilo.diplomski.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danilo.diplomski.exceptions.IllegalIdException;
import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.DTO.TeacherDTO;
import com.danilo.diplomski.models.data.Course;
import com.danilo.diplomski.models.data.Obligation;
import com.danilo.diplomski.models.data.Teacher;
import com.danilo.diplomski.repositories.TeacherRepository;
import com.danilo.diplomski.services.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	private TeacherRepository teacherRepo;

	@Autowired
	public TeacherServiceImpl(TeacherRepository teacherRepo) {
		this.teacherRepo = teacherRepo;
	}

	@Override
	public TeacherDTO createTeacher(TeacherDTO teacher) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Teacher teacherCreate = modelMapper.map(teacher, Teacher.class);

			teacher.setUserID(UUID.randomUUID().toString());

			Teacher saved = teacherRepo.save(teacherCreate);

			TeacherDTO response = modelMapper.map(saved, TeacherDTO.class);

			return response;
		} catch (Exception e) {
			throw new RuntimeException("Error in POST /students " + e.getMessage());
		}
	}

	// ova i student service impl find by user id treba da vraca dto, ali ne mogu da
	// konvertujem vise
	@Override
	public Teacher findByUserID(String teacherID) {

		Optional<Teacher> teacher = teacherRepo.findByUserID(teacherID);

		if (!teacher.isPresent())
			throw new IllegalIdException("Student with ID" + teacherID + "not found");

		return teacher.get();

	}

	@Override
	public List<CourseDTO> findAllCoursesForTeacher(String teacherID) {
		try {

			List<CourseDTO> response = new ArrayList<CourseDTO>();
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Teacher> teacher = teacherRepo.findByUserID(teacherID);

			if (!teacher.isPresent())
				throw new IllegalIdException("teacher with ID" + teacherID + "not found");

			Set<Course> courses = teacher.get().getCourses();
			for (Course c : courses) {
				CourseDTO c1 = modelMapper.map(c, CourseDTO.class);
				c1.setTeacherName(teacher.get().getName()+" " + teacher.get().getSurname());
				response.add(c1);
			}

			return response;

		} catch (Exception e) {
			throw new RuntimeException("Error in GET /teachers/+ID " + e.getMessage());
		}
	}

	@Override
	public List<ObligationDTO> findObligationsForDate(String teacherID, LocalDateTime date) {

		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			List<ObligationDTO> response = new ArrayList<ObligationDTO>();

			Optional<Teacher> teacher = teacherRepo.findByUserID(teacherID);
			if (!teacher.isPresent())
				throw new RuntimeException("Teacher not found. Id: " + teacherID);

			Set<Course> courses = teacher.get().getCourses();

			for (Course c : courses) {
				for (Obligation o : c.getObligations()) {
					if ((o.getDate().getDayOfMonth() == date.getDayOfMonth())
							&& (o.getDate().getMonth().equals(date.getMonth()))
							&& (o.getDate().getYear() == date.getYear())) {
						ObligationDTO obligat = modelMapper.map(o, ObligationDTO.class);
						obligat.setCourseName(o.getCourses().getName());
						response.add(obligat);
					}
				}
			}

			return response;
		} catch (Exception e) {
			throw new RuntimeException("Error in find obligations for teacher " + e.getMessage());
		}

	}
}
