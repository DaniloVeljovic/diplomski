package com.danilo.diplomski.services.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.danilo.diplomski.exceptions.StudentFailedToUpdateException;
import com.danilo.diplomski.exceptions.StudentNotCreatedException;
import com.danilo.diplomski.exceptions.StudentNotFoundException;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.data.Student;
import com.danilo.diplomski.models.kafka.StudentKafkaModel;
import com.danilo.diplomski.repositories.StudentRepository;
import com.danilo.diplomski.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private KafkaTemplate<String, StudentKafkaModel> studentProducer;
	private StudentRepository studentRepo;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepo, BCryptPasswordEncoder bCryptPasswordEncoder,
			KafkaTemplate<String, StudentKafkaModel> studentProducer) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.studentRepo = studentRepo;
		this.studentProducer = studentProducer;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Student> foundStudent = studentRepo.findByEmail(username);

		if (!foundStudent.isPresent())
			throw new UsernameNotFoundException(username);

		Student user = foundStudent.get();

		return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());

	}

	@Override
	public StudentDTO getUserDetailsByEmail(String email) {
		ModelMapper modelMapper = new ModelMapper();

		Optional<Student> foundStudent = studentRepo.findByEmail(email);

		if (!foundStudent.isPresent())
			throw new UsernameNotFoundException(email);

		StudentDTO user = modelMapper.map(foundStudent.get(), StudentDTO.class);

		return user;
	}

	@Override
	public StudentDTO createStudent(StudentDTO student) {
		try {

			student.setUserID(UUID.randomUUID().toString());

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Student studentToSave = modelMapper.map(student, Student.class);
			studentToSave.setEncryptedPassword(bCryptPasswordEncoder.encode(student.getPassword()));

			//TRANSACTIONAL OUTBOX BEGIN
			
			Student savedStudent = studentRepo.save(studentToSave);

			if (savedStudent == null)
				throw new StudentNotCreatedException("Failed to create student.");

			// na broker
			StudentKafkaModel toBroker = modelMapper.map(savedStudent, StudentKafkaModel.class);
			studentProducer.send("createUser", toBroker);

			//END
			
			StudentDTO savedDTO = modelMapper.map(savedStudent, StudentDTO.class);
			// savedDTO.setType(savedStudent.getClass().getSimpleName());

			return savedDTO;
		} catch (Exception e) {
			throw new StudentNotCreatedException("Error in POST /users/students. Error while creating student.");
		}
	}

	@Override
	public StudentDTO updateStudent(String id, StudentDTO student) {
		// TODO Auto-generated method stub
		try {
			Optional<Student> foundStudent = studentRepo.findByUserID(id);

			if (!foundStudent.isPresent())
				throw new StudentNotFoundException(
						"In method GET /students/{studentId}, student with id" + id + "is not found.");

			Student updatedStudent = foundStudent.get();

			updatedStudent.setName(student.getName());
			updatedStudent.setSurname(student.getSurname());
			updatedStudent.setNumIndex(student.getNumIndex());
			updatedStudent.setEmail(student.getEmail());
			updatedStudent.setYearOfStudy(student.getYearOfStudy());

			Student savedStudent = studentRepo.save(updatedStudent);

			if (savedStudent == null)
				throw new StudentFailedToUpdateException("Failed to update student.");

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			StudentDTO response = modelMapper.map(savedStudent, StudentDTO.class);
			// response.setType(savedStudent.getClass().getSimpleName());
			return response;

		} catch (Exception ex) {
			throw new RuntimeException("Failed to delete student with id:" + id + ex.getLocalizedMessage());
		}
	}

	@Override
	public StudentDTO findByStudentID(String id) {

		try {
			Optional<Student> returnStudent = studentRepo.findByUserID(id);

			if (!returnStudent.isPresent())
				throw new StudentNotFoundException(
						"In method GET /students/{studentId}, student with id" + id + "is not found.");

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			StudentDTO response = modelMapper.map(returnStudent.get(), StudentDTO.class);
			// response.setType(returnStudent.get().getClass().getSimpleName());
			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in finding student with ID: " + id + ex.getLocalizedMessage());
		}

	}

	@Override
	public boolean deleteStudent(String id) {
		try {
			studentRepo.deleteByUserID(id);
			return true;
		} catch (Exception ea) {
			throw new RuntimeException("Failed to delete student with id:" + id);
		}
	}

}
