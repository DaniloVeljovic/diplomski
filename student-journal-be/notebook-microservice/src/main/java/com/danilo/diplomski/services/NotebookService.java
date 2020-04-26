package com.danilo.diplomski.services;

import java.util.List;

import com.danilo.diplomski.models.DTO.NotebookDTO;
import com.danilo.diplomski.models.DTO.RegistrationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;

public interface NotebookService {

	NotebookDTO createNewNotebook(NotebookDTO notebook, String userID);
	
	NotebookDTO updateNotebook(NotebookDTO notebooktoUpdate, String userID);

	List<NotebookDTO> findByUserIDAllNotebooks(String userID);

	NotebookDTO findByUserIDNotebook(String userID, String course);

	StudentDTO createStudent(StudentDTO studentToCreate);

	void deleteByCourseAndUserID(String course, String userID);

	void deleteUserByUserID(String userID);

	void createNotebook(RegistrationDTO res);
	
	//VIDECEMO ZA OVO
	//NotebookResponseModel createResponse(Notebook newNotebook);
	
	//List<NotebookResponseModel> createResponses(Set<Notebook> newNotebook);

	
}
