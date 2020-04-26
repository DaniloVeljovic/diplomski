package com.danilo.diplomski.messaging;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.danilo.diplomski.models.DTO.RegistrationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.DTO.TeacherDTO;
import com.danilo.diplomski.models.kafka.RegistrationKafkaModel;
import com.danilo.diplomski.models.kafka.StudentKafkaModel;
import com.danilo.diplomski.models.kafka.TeacherKafkaModel;
import com.danilo.diplomski.services.StudentService;
import com.danilo.diplomski.services.TeacherService;

@Component
public class Consumers {

	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;

	@KafkaListener(topics = "createUser", containerFactory = "studentKafkaListenerContainerFactory", groupId = "group-90")
	public void createStudent(StudentKafkaModel student) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		StudentDTO studentToCreate = modelMapper.map(student, StudentDTO.class);

		StudentDTO createdStudent = studentService.createStudent(studentToCreate);

		System.out.println(createdStudent);

	}

	@KafkaListener(topics = "saga_fail", containerFactory = "registrationKafkaListenerContainerFactory", groupId = "group-three")
	public void failToCreateNotebook(RegistrationKafkaModel reg) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		RegistrationDTO resFail = modelMapper.map(reg, RegistrationDTO.class);

		// ili da joj postavim status kao FAILED
		studentService.deleteRegistration(resFail);
	}

	@KafkaListener(topics = "saga_success", containerFactory = "registrationKafkaListenerContainerFactory", groupId = "group-four")
	public void successToCreateNotebook(RegistrationKafkaModel reg) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		RegistrationDTO resSuccess = modelMapper.map(reg, RegistrationDTO.class);

		studentService.confirmRegistration(resSuccess);
	}

	@KafkaListener(topics = "second_topic", containerFactory = "teacherKafkaListenerContainerFactory", groupId = "group-one")
	public void createTeacher(TeacherKafkaModel newTeacher) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		TeacherDTO teacher = modelMapper.map(newTeacher, TeacherDTO.class);

		TeacherDTO createdTeacher = teacherService.createTeacher(teacher);

		System.out.println(createdTeacher);
	}

}
