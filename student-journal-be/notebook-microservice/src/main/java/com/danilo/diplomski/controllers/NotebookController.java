package com.danilo.diplomski.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.danilo.diplomski.models.DTO.NotebookDTO;
import com.danilo.diplomski.models.UIModels.NotebookRequestModel;
import com.danilo.diplomski.models.UIModels.NotebookResponseModel;
import com.danilo.diplomski.models.UIModels.StudentResponseModel;
import com.danilo.diplomski.services.NotebookService;

@RestController
@RequestMapping(path = "/notebooks")
public class NotebookController {

	@Autowired
	private NotebookService notebookService;

	// get all notebooks for a user
	@GetMapping(path = "/students/{userID}")
	public ResponseEntity<List<NotebookResponseModel>> getAllNotebooksForUser(@PathVariable String userID) {
		List<NotebookDTO> foundNotebooks = notebookService.findByUserIDAllNotebooks(userID);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<NotebookResponseModel> response = new ArrayList<NotebookResponseModel>();

		for (NotebookDTO n : foundNotebooks) {
			response.add(modelMapper.map(n, NotebookResponseModel.class));
		}

		return new ResponseEntity<List<NotebookResponseModel>>(response, HttpStatus.OK);
	}

	// get a notebook for a user
	// svesku referenciraj po ID a ne po imenu predmeta
	@GetMapping(path = "/{course}/students/{userID}")
	public ResponseEntity<NotebookResponseModel> getNotebookForUser(@PathVariable String userID,
			@PathVariable String course) {
		NotebookDTO foundNotebooks = notebookService.findByUserIDNotebook(userID, course);

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		NotebookResponseModel response = modelMapper.map(foundNotebooks, NotebookResponseModel.class);

		return new ResponseEntity<NotebookResponseModel>(response, HttpStatus.OK);
	}

	// create a notebook for a user
	@PostMapping(path = "/students/{userID}")
	public ResponseEntity<NotebookResponseModel> createNotebookForUser(@PathVariable String userID,
			@RequestBody NotebookRequestModel newNotebook) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		NotebookDTO notebookToCreate = modelMapper.map(newNotebook, NotebookDTO.class);

		NotebookDTO createdNotebook = notebookService.createNewNotebook(notebookToCreate, userID);

		NotebookResponseModel response = modelMapper.map(createdNotebook, NotebookResponseModel.class);

		return new ResponseEntity<NotebookResponseModel>(response, HttpStatus.CREATED);

	}

	// update a notebook for a user
	@PutMapping(path = "/students/{userID}")
	public ResponseEntity<NotebookResponseModel> updateNotebookForUser(@RequestBody NotebookRequestModel newNotebook,
			@PathVariable String userID) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		NotebookDTO notebookToUpdate = modelMapper.map(newNotebook, NotebookDTO.class);

		NotebookDTO updatedNotebook = notebookService.updateNotebook(notebookToUpdate, userID);

		NotebookResponseModel response = modelMapper.map(updatedNotebook, NotebookResponseModel.class);

		return new ResponseEntity<NotebookResponseModel>(response, HttpStatus.OK);
	}

	// delete a notebook for a user
	@DeleteMapping(path = "/{course}/students/{userID}")
	public ResponseEntity<NotebookResponseModel> deleteNotebookForUser(@PathVariable String course,
			@PathVariable String userID) {
		notebookService.deleteByCourseAndUserID(course, userID);
		return new ResponseEntity<NotebookResponseModel>(HttpStatus.NO_CONTENT);
	}

	// delete user and then delete all his notebooks
	@DeleteMapping(path = "/students/{userID}")
	public ResponseEntity<StudentResponseModel> deleteAllNotebooksForUser(@PathVariable String userID) {
		notebookService.deleteUserByUserID(userID);
		return new ResponseEntity<StudentResponseModel>(HttpStatus.NO_CONTENT);
	}

}
