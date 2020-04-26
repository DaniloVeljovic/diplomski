package com.danilo.diplomski.messaging;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.danilo.diplomski.models.DTO.RegistrationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.kafka.RegistrationKafkaModel;
import com.danilo.diplomski.models.kafka.StudentKafkaModel;
import com.danilo.diplomski.services.NotebookService;

@Component
public class Consumers {

	@Autowired
	private NotebookService notebookService;

	@KafkaListener(topics = "createUser", containerFactory = "studentKafkaListenerContainerFactory", groupId = "test-consumer-group")
	public void createStudent(StudentKafkaModel newStudent) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		System.out.println(newStudent);

		StudentDTO studentToCreate = modelMapper.map(newStudent, StudentDTO.class);

		notebookService.createStudent(studentToCreate);
	}

	@KafkaListener(topics = "enrol", containerFactory = "registrationKafkaListenerContainerFactory", groupId = "group-six")
	public void failToCreateNotebook(RegistrationKafkaModel reg) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		RegistrationDTO resFail = modelMapper.map(reg, RegistrationDTO.class);

		// ili da joj postavim status kao FAILED
		notebookService.createNotebook(resFail);
	}

}
