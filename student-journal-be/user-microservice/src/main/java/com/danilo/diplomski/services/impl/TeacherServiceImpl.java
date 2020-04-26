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

import com.danilo.diplomski.exceptions.StudentNotCreatedException;
import com.danilo.diplomski.exceptions.TeacherFailedToDeleteException;
import com.danilo.diplomski.exceptions.TeacherFailedToUpdateException;
import com.danilo.diplomski.exceptions.TeacherNotCreatedException;
import com.danilo.diplomski.exceptions.TeacherNotFoundException;
import com.danilo.diplomski.models.DTO.TeacherDTO;
import com.danilo.diplomski.models.data.Teacher;
import com.danilo.diplomski.models.kafka.TeacherKafkaModel;
import com.danilo.diplomski.repositories.TeacherRepository;
import com.danilo.diplomski.services.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {

	private TeacherRepository teacherRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private KafkaTemplate<String, TeacherKafkaModel> teacherProducer;

	@Autowired
	public TeacherServiceImpl(TeacherRepository teacherRepo, BCryptPasswordEncoder bCryptPasswordEncoder,
			KafkaTemplate<String, TeacherKafkaModel> teacherProducer) {
		this.teacherRepo = teacherRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.teacherProducer = teacherProducer;
	}

	@Override
	public TeacherDTO createTeacher(TeacherDTO teacher) {
		try {
			teacher.setUserID(UUID.randomUUID().toString());

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Teacher teacherToSave = modelMapper.map(teacher, Teacher.class);
			teacherToSave.setEncryptedPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));

			Teacher savedTeacher = teacherRepo.save(teacherToSave);

			/*OVO JE GRUPISANO OVAKO, DA MIMIKUJE TRANSACTIONAL OUTBOX PATTERN*/
			if (savedTeacher == null)
				throw new TeacherNotCreatedException("Failed to create teacher.");

			// na broker
			TeacherKafkaModel toBroker = modelMapper.map(savedTeacher, TeacherKafkaModel.class);
			teacherProducer.send("second_topic", toBroker);

			TeacherDTO savedDTO = modelMapper.map(savedTeacher, TeacherDTO.class);

			return savedDTO;
		} catch (Exception e) {
			throw new StudentNotCreatedException("Error in POST /users/students. Error while creating student.");
		}
	}

	@Override
	public TeacherDTO updateTeacher(String id, TeacherDTO updatedTeacher) {
		try {
			Optional<Teacher> foundTeacher = teacherRepo.findByUserID(id);

			if (!foundTeacher.isPresent())
				throw new TeacherNotFoundException(
						"In method GET /teacher/{teacherId}, teacher with id" + id + "is not found.");

			Teacher teacher = foundTeacher.get();

			teacher.setName(updatedTeacher.getName());
			teacher.setSurname(updatedTeacher.getSurname());
			teacher.setNumOfPublishedWorks(updatedTeacher.getNumOfPublishedWorks());

			Teacher savedTeacher = teacherRepo.save(teacher);

			if (savedTeacher == null)
				throw new TeacherFailedToUpdateException("Failed to update teacher.");

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			TeacherDTO response = modelMapper.map(savedTeacher, TeacherDTO.class);
			// response.setType(savedTeacher.getClass().getSimpleName());

			return response;

		} catch (Exception ex) {
			throw new RuntimeException("Failed to delete teacher with id:" + id + ex.getLocalizedMessage());
		}
	}

	@Override
	public boolean deleteByTeacherID(String id) {
		try {
			teacherRepo.deleteByUserID(id);
			return true;
		} catch (Exception ex) {
			throw new TeacherFailedToDeleteException(
					"Failed to delete student with id: " + id + "\n message: " + ex.getMessage());
		}
	}

	@Override
	public TeacherDTO findByTeacherID(String id) {
		try {
			Optional<Teacher> returnTeacher = teacherRepo.findByUserID(id);

			if (!returnTeacher.isPresent())
				throw new TeacherNotFoundException(
						"In method GET /teacher/{teacherID}, teacher with id" + id + "is not found.");

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			TeacherDTO response = modelMapper.map(returnTeacher.get(), TeacherDTO.class);
			// response.setType(returnTeacher.get().getClass().getSimpleName());
			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in finding student with ID: " + id + ex.getLocalizedMessage());
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Teacher> foundTeacher = teacherRepo.findByEmail(username);

		if (!foundTeacher.isPresent())
			throw new UsernameNotFoundException(username);

		Teacher user = foundTeacher.get();

		return new User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());

	}

	@Override
	public TeacherDTO getUserDetailsByEmail(String email) {
		ModelMapper modelMapper = new ModelMapper();

		Optional<Teacher> foundTeacher = teacherRepo.findByEmail(email);

		if (!foundTeacher.isPresent())
			throw new UsernameNotFoundException(email);

		TeacherDTO user = modelMapper.map(foundTeacher.get(), TeacherDTO.class);

		return user;
	}

}

/**/
