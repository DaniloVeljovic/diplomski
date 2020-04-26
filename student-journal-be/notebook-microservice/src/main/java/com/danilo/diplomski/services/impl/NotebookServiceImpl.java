package com.danilo.diplomski.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.danilo.diplomski.exceptions.NotebookNotFoundException;
import com.danilo.diplomski.exceptions.StudentNotFoundException;
import com.danilo.diplomski.models.DTO.NotebookDTO;
import com.danilo.diplomski.models.DTO.RegistrationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.data.Notebook;
import com.danilo.diplomski.models.data.Student;
import com.danilo.diplomski.models.kafka.RegistrationKafkaModel;
import com.danilo.diplomski.repositories.NotebookRepository;
import com.danilo.diplomski.repositories.StudentRepository;
import com.danilo.diplomski.services.NotebookService;

@Service
public class NotebookServiceImpl implements NotebookService {

	private NotebookRepository notebookRepo;
	private StudentRepository studentRepo;
	
	@Autowired
	private KafkaTemplate<String, RegistrationKafkaModel> registrationProducer;

	@Autowired
	public NotebookServiceImpl(NotebookRepository nr, StudentRepository sr) {
		this.notebookRepo = nr;
		this.studentRepo = sr;
	}

	@Override
	public NotebookDTO createNewNotebook(NotebookDTO notebook, String userID) {

		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Student> student = studentRepo.findByUserID(userID);
			if (!student.isPresent())
				throw new StudentNotFoundException("Student with userID:" + userID + " not found.");

			Notebook newNotebook = modelMapper.map(notebook, Notebook.class);

			newNotebook.setStudent(student.get());

			notebookRepo.save(newNotebook);

			NotebookDTO createdNotebook = modelMapper.map(newNotebook, NotebookDTO.class);

			return createdNotebook;
		} catch (Exception e) {
			throw new RuntimeException("Error in POST /notebooks/students/" + userID + " error " + e.getMessage());
		}

	}

	@Override
	public NotebookDTO updateNotebook(NotebookDTO newNotebook, String userID) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Student> student = studentRepo.findByUserID(userID);
			if (!student.isPresent())
				throw new StudentNotFoundException("Student with userID:" + userID + " not found.");

			Notebook notebookToUpdate = null;
			for (Notebook n : student.get().getHasNotebooks()) {
				if (n.getCourse().equals(newNotebook.getCourse())) {
					notebookToUpdate = n;
				}
			}

			if (notebookToUpdate == null) {
				throw new NotebookNotFoundException(
						"Notebook " + newNotebook.getCourse() + " is not found for userID " + userID);
			}

			notebookToUpdate.setText(newNotebook.getText());

			Notebook updated = notebookRepo.save(notebookToUpdate);

			NotebookDTO response = modelMapper.map(updated, NotebookDTO.class);

			return response;

		} catch (Exception e) {
			throw new RuntimeException("Error in PUT /notebooks/students/" + userID);
		}
	}

	@Override
	public List<NotebookDTO> findByUserIDAllNotebooks(String userID) {

		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Student> student = studentRepo.findByUserID(userID);
			if (!student.isPresent())
				throw new StudentNotFoundException("Student with userID:" + userID + " not found.");

			List<Notebook> studentsNotes = student.get().getHasNotebooks();

			List<NotebookDTO> response = new ArrayList<>();

			for (Notebook n : studentsNotes) {
				response.add(modelMapper.map(n, NotebookDTO.class));
			}

			return response;
		} catch (Exception e) {
			throw new RuntimeException("Error in GET /notebooks/students/" + userID);
		}

	}

	@Override
	public NotebookDTO findByUserIDNotebook(String userID, String course) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Student> student = studentRepo.findByUserID(userID);
			if (!student.isPresent())
				throw new StudentNotFoundException("Student with userID:" + userID + " not found.");

			List<Notebook> studentsNotes = student.get().getHasNotebooks();

			NotebookDTO response = null;
			for (Notebook n : studentsNotes) {
				if (n.getCourse().equals(course))
					response = modelMapper.map(n, NotebookDTO.class);
			}

			if (response == null)
				throw new NotebookNotFoundException(
						"Notebook for course : " + course + " for user: " + userID + " is not found");

			return response;
		} catch (Exception e) {
			throw new RuntimeException("Error in GET /notebooks" + course + "/students/" + userID);
		}

	}

	@Override
	public StudentDTO createStudent(StudentDTO studentToCreate) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Student student = modelMapper.map(studentToCreate, Student.class);

			Student savedStudent = studentRepo.save(student);

			StudentDTO response = modelMapper.map(savedStudent, StudentDTO.class);
			return response;

		} catch (Exception e) {
			throw new RuntimeException("Error in POST /notebooks/students/");
		}

	}

	@Override
	public void deleteByCourseAndUserID(String course, String userID) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Student> student = studentRepo.findByUserID(userID);
			if (!student.isPresent())
				throw new StudentNotFoundException("Student with userID:" + userID + " not found.");

			notebookRepo.deleteByCourseAndStudent(course, student.get());

		} catch (Exception e) {
			throw new RuntimeException("Error in DELETE /notebooks/course/students/userID " + e.getMessage());
		}

	}

	@Override
	public void deleteUserByUserID(String userID) {
		try {
			studentRepo.deleteByUserID(userID);
		} catch (Exception e) {
			throw new RuntimeException("Error in DELETE /notebooks/students/ " + e.getMessage());
		}

	}

	@Override
	public void createNotebook(RegistrationDTO req) {
		
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Student> student = studentRepo.findByUserID(req.getUserID());
			if (!student.isPresent())
				throw new StudentNotFoundException("Student with userID:" + req.getUserID() + " not found.");

			Notebook newNotebook = new Notebook();
			newNotebook.setCourse(req.getCourseID());
			newNotebook.setText("");
			newNotebook.setStudent(student.get());

			Notebook res = notebookRepo.save(newNotebook);
			RegistrationKafkaModel toBroker = modelMapper.map(req, RegistrationKafkaModel.class);
			
			if(res == null)
			{
				//emituj na failed topic
				registrationProducer.send("saga_fail",toBroker);
				throw new RuntimeException("Could not finish saga. Failed to create notebook.");
			}
			registrationProducer.send("saga_success",toBroker);
			//emituj na success
			
		} catch (Exception e) {
			throw new RuntimeException("Error in POST /notebooks/students/" + req.getUserID() + " error " + e.getMessage());
		}

		
	}
}
