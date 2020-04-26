package com.danilo.diplomski.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.UIModels.CourseRequestModel;
import com.danilo.diplomski.models.UIModels.CourseResponseModel;
import com.danilo.diplomski.models.UIModels.ObligationRequestModel;
import com.danilo.diplomski.models.UIModels.ObligationResponseModel;
import com.danilo.diplomski.models.UIModels.StudentResponseModel;
import com.danilo.diplomski.services.CourseService;
import com.danilo.diplomski.services.StudentService;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentService studentService;

	// C
	// kreiraj kurs
	@PostMapping(path = "/teachers/{teacherID}")
	public ResponseEntity<CourseResponseModel> createCourse(@RequestBody CourseRequestModel reqCourse,
			@PathVariable String teacherID) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		CourseDTO courseToCreate = modelMapper.map(reqCourse, CourseDTO.class);
		CourseDTO createdCourse = courseService.createCourse(courseToCreate, teacherID);

		CourseResponseModel response = modelMapper.map(createdCourse, CourseResponseModel.class);

		return new ResponseEntity<CourseResponseModel>(response, HttpStatus.CREATED);
	}

	// R
	@GetMapping
	public ResponseEntity<List<CourseResponseModel>> getAllCourses() {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<CourseDTO> courses = courseService.findAllCourses();

		List<CourseResponseModel> response = new ArrayList<CourseResponseModel>();

		for (CourseDTO c : courses) {
			response.add(modelMapper.map(c, CourseResponseModel.class));
		}

		return new ResponseEntity<List<CourseResponseModel>>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/{courseID}/role/{role}/userID/{userID}")
	public ResponseEntity<CourseResponseModel> getCourse(@PathVariable String courseID,
			@PathVariable String role, @PathVariable String userID) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		if (role.equals("student")) {
			studentService.checkIfEnroled(userID, courseID);
		}

		CourseDTO foundCourse = courseService.findCourseByID(courseID);

		CourseResponseModel response = modelMapper.map(foundCourse, CourseResponseModel.class);

		return new ResponseEntity<CourseResponseModel>(response, HttpStatus.OK);
	}

	@PutMapping(path = "/{courseID}")
	public ResponseEntity<CourseResponseModel> updateCourse(@PathVariable String courseID,
			@RequestBody CourseRequestModel desc) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		CourseDTO courseToUpdate = modelMapper.map(desc, CourseDTO.class);

		CourseDTO updatedCourse = courseService.updateCourse(courseToUpdate);

		CourseResponseModel response = modelMapper.map(updatedCourse, CourseResponseModel.class);

		return new ResponseEntity<CourseResponseModel>(response, HttpStatus.OK);
	}

	// D
	@DeleteMapping(path = "/{courseID}")
	public ResponseEntity<CourseResponseModel> deleteCourse(@PathVariable String courseID) {

		courseService.deleteByCourseID(courseID);

		return new ResponseEntity<CourseResponseModel>(HttpStatus.NO_CONTENT);
	}

	// OBLIGATIONS

	@PostMapping(path = "/{courseID}/obligations")
	public ResponseEntity<ObligationResponseModel> createObligation(@RequestBody ObligationRequestModel reqObligation,
			@PathVariable String courseID) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ObligationDTO obligationToAdd = modelMapper.map(reqObligation, ObligationDTO.class);

		ObligationDTO addedObligation = courseService.addObligationToCourse(obligationToAdd, courseID);

		ObligationResponseModel response = modelMapper.map(addedObligation, ObligationResponseModel.class);

		return new ResponseEntity<ObligationResponseModel>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "/{courseID}/obligations")
	public ResponseEntity<List<ObligationResponseModel>> getObligationsForCourse(@PathVariable String courseID) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<ObligationDTO> obligations = courseService.getObligationsForCourse(courseID);

		List<ObligationResponseModel> response = new ArrayList<ObligationResponseModel>();

		for (ObligationDTO o : obligations) {
			response.add(modelMapper.map(o, ObligationResponseModel.class));
		}

		return new ResponseEntity<List<ObligationResponseModel>>(response, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{courseID}/students")
	public ResponseEntity<List<StudentResponseModel>> getStudentsForCourse(@PathVariable String courseID)
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<StudentDTO> students = courseService.getStudentsForCourse(courseID);
		
		List<StudentResponseModel> response = new ArrayList<StudentResponseModel>();

		for (StudentDTO o : students) {
			response.add(modelMapper.map(o, StudentResponseModel.class));
		}

		return new ResponseEntity<List<StudentResponseModel>>(response, HttpStatus.OK);
		
	}

	// POCETAK SAGE
//ENROL STUDENT TO A COURSE
	@PostMapping(path = "/{courseID}/enrol/{studentID}")
	public ResponseEntity<CourseResponseModel> enrolStudent(@PathVariable String courseID,
			@PathVariable String studentID) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		// CourseDTO course =
		courseService.enrolStudent(courseID, studentID);

		// mozda ovde da ne vracam informacije o kursu?
		// CourseResponseModel response = modelMapper.map(course,
		// CourseResponseModel.class);

		HttpHeaders headers = new HttpHeaders();
		headers.set("message", "Your request has been submitted. Refresh the page in a couple of seconds.");
		return new ResponseEntity<CourseResponseModel>(null, headers, HttpStatus.NO_CONTENT);
	}
}
