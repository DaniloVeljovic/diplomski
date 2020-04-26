package com.danilo.diplomski.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.UIModels.CourseResponseModel;
import com.danilo.diplomski.models.UIModels.ObligationResponseModel;
import com.danilo.diplomski.services.StudentService;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	// get all courses for a student
	@GetMapping(path = "/{studentID}/courses")
	public ResponseEntity<List<CourseResponseModel>> getCoursesForUser(@PathVariable String studentID) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<CourseDTO> courses = studentService.findAllCoursesForStudent(studentID);
		List<CourseResponseModel> response = new ArrayList<CourseResponseModel>();

		for (CourseDTO c : courses) {
			response.add(modelMapper.map(c, CourseResponseModel.class));
		}

		return new ResponseEntity<List<CourseResponseModel>>(response, HttpStatus.OK);
	}

	// delete a student -> delete all his registrations TODO
	// retrieve all obligations for a student for a specific date
	@GetMapping(path = "/{studentID}/date/{date}")
	public ResponseEntity<List<ObligationResponseModel>> getAllObligationsForAStudent(@PathVariable String studentID,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<ObligationDTO> foundObligations = studentService.findAllObligationsForDate(studentID, date);
		List<ObligationResponseModel> response = new ArrayList<ObligationResponseModel>();

		for (ObligationDTO o : foundObligations) {
			response.add(modelMapper.map(o, ObligationResponseModel.class));
		}

		return new ResponseEntity<List<ObligationResponseModel>>(response, HttpStatus.OK);

	}

}
