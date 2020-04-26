package com.danilo.diplomski.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.DTO.TeacherDTO;
import com.danilo.diplomski.models.UImodels.StudentRequestModel;
import com.danilo.diplomski.models.UImodels.StudentResponseModel;
import com.danilo.diplomski.models.UImodels.TeacherRequestModel;
import com.danilo.diplomski.models.UImodels.TeacherResponseModel;
import com.danilo.diplomski.services.StudentService;
import com.danilo.diplomski.services.TeacherService;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;

	// get student
	@GetMapping(path = "/students/{studentId}")
	public ResponseEntity<StudentResponseModel> getStudent(@PathVariable String studentId) {
		StudentDTO returnStudent = studentService.findByStudentID(studentId);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		StudentResponseModel response = modelMapper.map(returnStudent, StudentResponseModel.class);

		return new ResponseEntity<StudentResponseModel>(response, HttpStatus.OK);
	}

	// create student
	@PostMapping(path = "/students")
	public ResponseEntity<StudentResponseModel> createStudent(@RequestBody StudentRequestModel reqStudent) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		StudentDTO student = modelMapper.map(reqStudent, StudentDTO.class);

		StudentDTO createdStudent = studentService.createStudent(student);

		StudentResponseModel response = modelMapper.map(createdStudent, StudentResponseModel.class);

		return new ResponseEntity<StudentResponseModel>(response, HttpStatus.OK);
	}

	// update student
	@PutMapping(path = "/students/{id}")
	public ResponseEntity<StudentResponseModel> updateStudent(@PathVariable String id,
			@RequestBody StudentRequestModel reqStudent) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		StudentDTO student = modelMapper.map(reqStudent, StudentDTO.class);

		StudentDTO updatedStudent = studentService.updateStudent(id, student);

		StudentResponseModel response = modelMapper.map(updatedStudent, StudentResponseModel.class);

		return new ResponseEntity<StudentResponseModel>(response, HttpStatus.OK);
	}

	// delete student
	@DeleteMapping(path = "/students/{id}")
	public ResponseEntity<StudentResponseModel> deleteStudent(@PathVariable String id) {
		studentService.deleteStudent(id);

		return new ResponseEntity<StudentResponseModel>(HttpStatus.NO_CONTENT);
	}

	// delete teacher
	@DeleteMapping(path = "/teachers/{id}")
	public ResponseEntity<TeacherResponseModel> deleteTeacher(@PathVariable String id) {
		teacherService.deleteByTeacherID(id);

		return new ResponseEntity<TeacherResponseModel>(HttpStatus.NO_CONTENT);
	}

	// get teacher
	@GetMapping(path = "/teachers/{teacherId}")
	public ResponseEntity<TeacherResponseModel> getTeacher(@PathVariable String teacherId) {
		TeacherDTO returnTeacher = teacherService.findByTeacherID(teacherId);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		TeacherResponseModel response = modelMapper.map(returnTeacher, TeacherResponseModel.class);

		return new ResponseEntity<TeacherResponseModel>(response, HttpStatus.OK);
	}

	// create teacher
	@PostMapping(path = "/teachers")
	public ResponseEntity<TeacherResponseModel> createTeacher(@RequestBody TeacherRequestModel reqTeacher) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		TeacherDTO teacher = modelMapper.map(reqTeacher, TeacherDTO.class);

		TeacherDTO createdTeacher = teacherService.createTeacher(teacher);

		TeacherResponseModel response = modelMapper.map(createdTeacher, TeacherResponseModel.class);

		return new ResponseEntity<TeacherResponseModel>(response, HttpStatus.OK);

	}

	// update teacher
	@PutMapping(path = "/teachers/{id}")
	public ResponseEntity<TeacherResponseModel> updateTeacher(@PathVariable String id,
			@RequestBody TeacherRequestModel reqTeacher) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		TeacherDTO teacher = modelMapper.map(reqTeacher, TeacherDTO.class);

		TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacher);

		TeacherResponseModel response = modelMapper.map(updatedTeacher, TeacherResponseModel.class);

		return new ResponseEntity<TeacherResponseModel>(response, HttpStatus.OK);
	}

	// login
	// register

}
