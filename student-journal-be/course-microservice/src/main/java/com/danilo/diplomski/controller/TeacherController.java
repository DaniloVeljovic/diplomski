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
import com.danilo.diplomski.services.TeacherService;

@RestController
@RequestMapping(path ="/teachers")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping(path = "/{teacherID}")
	public ResponseEntity<List<CourseResponseModel>> getAllCoursesTeacher(@PathVariable String teacherID) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<CourseDTO> courses = teacherService.findAllCoursesForTeacher(teacherID);

		List<CourseResponseModel> response = new ArrayList<CourseResponseModel>();

		for (CourseDTO c : courses) {
			response.add(modelMapper.map(c, CourseResponseModel.class));
		}

		return new ResponseEntity<List<CourseResponseModel>>(response, HttpStatus.OK);
	}
	
	@GetMapping(path="/{teacherID}/obligations/{date}")
	public ResponseEntity<List<ObligationResponseModel>> getAllObligationsForDate(@PathVariable String teacherID, 
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date)
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<ObligationDTO> obligations = teacherService.findObligationsForDate(teacherID, date);
		
		List<ObligationResponseModel> response = new ArrayList<ObligationResponseModel>();
		
		for(ObligationDTO o : obligations) {
			response.add(modelMapper.map(o, ObligationResponseModel.class));
		}
		
		return new ResponseEntity<List<ObligationResponseModel>>(response, HttpStatus.OK);
		
	}
}
