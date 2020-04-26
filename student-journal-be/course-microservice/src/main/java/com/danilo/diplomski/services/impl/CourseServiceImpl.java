package com.danilo.diplomski.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.danilo.diplomski.exceptions.IllegalIdException;
import com.danilo.diplomski.models.RegistrationStatus;
import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.data.Course;
import com.danilo.diplomski.models.data.Obligation;
import com.danilo.diplomski.models.data.Student;
import com.danilo.diplomski.models.data.Teacher;
import com.danilo.diplomski.models.data.junctiontables.Registration;
import com.danilo.diplomski.models.kafka.RegistrationKafkaModel;
import com.danilo.diplomski.repositories.CourseRepository;
import com.danilo.diplomski.repositories.RegistrationRepository;
import com.danilo.diplomski.services.CourseService;
import com.danilo.diplomski.services.StudentService;
import com.danilo.diplomski.services.TeacherService;

@Service
public class CourseServiceImpl implements CourseService {

	private TeacherService teacherService;
	private CourseRepository courseRepo;
	private StudentService studentService;
	private RegistrationRepository registrationRepo;
	private KafkaTemplate<String, RegistrationKafkaModel> registrationProducer;

	@Autowired
	public CourseServiceImpl(TeacherService teacherService, CourseRepository repo, StudentService studentService,
			RegistrationRepository registrationRepo,
			KafkaTemplate<String, RegistrationKafkaModel> registrationProducer) {
		this.teacherService = teacherService;
		this.courseRepo = repo;
		this.studentService = studentService;
		this.registrationRepo = registrationRepo;
		this.registrationProducer = registrationProducer;
	}

	@Override
	public CourseDTO createCourse(CourseDTO courseToCreate, String teacherID) throws IllegalArgumentException {
		try {

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Teacher teacherWhoAddedCourse = teacherService.findByUserID(teacherID);

			if (teacherWhoAddedCourse == null)
				throw new IllegalIdException("Illegal ID for teacher, not found.");

			Course userCourse = modelMapper.map(courseToCreate, Course.class);

			userCourse.setCourseID(UUID.randomUUID().toString());

			userCourse.setTeacher(teacherWhoAddedCourse);

			Course saved = courseRepo.save(userCourse);

			CourseDTO response = modelMapper.map(saved, CourseDTO.class);

			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in POST /courses/teacher/teacherId. Message:" + ex.getMessage());
		}

	}

	@Override
	public List<CourseDTO> findAllCourses() {
		try {
			List<CourseDTO> response = new ArrayList<CourseDTO>();

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Iterable<Course> courses = courseRepo.findAll();

			for (Course c : courses) {
				CourseDTO r = modelMapper.map(c, CourseDTO.class);
				r.setTeacherName(c.getTeacher().getName()+" "+c.getTeacher().getSurname());
				response.add(r);
			}
			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in GET /courses Message:" + ex.getMessage());
		}

	}

	@Override
	public CourseDTO findCourseByID(String courseID) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Course> course = courseRepo.findByCourseID(courseID);

			if (!course.isPresent())
				throw new RuntimeException("Course with the given ID is not found.");

			CourseDTO response = modelMapper.map(course.get(), CourseDTO.class);

			response.setTeacherName(course.get().getTeacher().getName()+" " + course.get().getTeacher().getName());
			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in GET /courses/courseID. Message:" + ex.getMessage());
		}
	}

	@Override
	public CourseDTO updateCourse(CourseDTO courseToUpdate) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Course> course = courseRepo.findByCourseID(courseToUpdate.getCourseID());

			if (!course.isPresent())
				throw new RuntimeException("Course with the given ID is not found.");

			Course update = course.get();
			update.setDescription(courseToUpdate.getDescription());
			update.setName(courseToUpdate.getName());
			update.setSemester(courseToUpdate.getSemester());

			Course updated = courseRepo.save(update);

			CourseDTO response = modelMapper.map(updated, CourseDTO.class);

			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in PUT /courses/courseID. Message:" + ex.getMessage());
		}
	}

	@Override
	public void deleteByCourseID(String courseID) {
		try {

			courseRepo.deleteByCourseID(courseID);

		} catch (Exception ex) {
			throw new RuntimeException("Error in DELETE /courses/courseID. Message:" + ex.getMessage());
		}
	}

	@Override
	public ObligationDTO addObligationToCourse(ObligationDTO obligationToAdd, String courseID) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Course> course = courseRepo.findByCourseID(courseID);

			if (!course.isPresent())
				throw new RuntimeException("Course with the given ID is not found.");

			Course found = course.get();

			Obligation obligation = modelMapper.map(obligationToAdd, Obligation.class);

			obligation.setObligationID(UUID.randomUUID().toString());

			obligation.setCourses(found);

			found.getObligations().add(obligation);

			courseRepo.save(found);

			ObligationDTO response = modelMapper.map(obligation, ObligationDTO.class);

			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in POST /courses/courseID/obligations. Message:" + ex.getMessage());
		}
	}

//OKIDAC SAGE
	@Override
	public CourseDTO enrolStudent(String courseID, String studentID) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Course> course = courseRepo.findByCourseID(courseID);

			if (!course.isPresent())
				throw new RuntimeException("Course with the given ID is not found.");

			Course found = course.get();

			Student student = studentService.findByUserID(studentID);

			Set<Registration> regs = student.getRegistrations();
			
			boolean flag = true;
			for(Registration r : regs)
			{
				if(r.getCourses().getCourseID().equals(courseID)) {
					throw new RuntimeException("You already tried to enrol to this course.");
				}
			}
				
			
			Registration registration = new Registration();
			registration.setCourses(found);
			registration.setStudent(student);
			registration.setStatus(RegistrationStatus.PENDING);

			// 1. update local db
			registrationRepo.save(registration);

			RegistrationKafkaModel toBroker = new RegistrationKafkaModel();
			toBroker.setCourseID(registration.getCourses().getName());
			toBroker.setUserID(registration.getStudent().getUserID());

			// 2. send the message
			registrationProducer.send("enrol", toBroker);

			// mozda da ne vracas nista
			CourseDTO response = modelMapper.map(course.get(), CourseDTO.class);

			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error in POST /courses/courseID/enrol/studentID. Message:" + ex.getMessage());
		}
	}

	@Override
	public List<ObligationDTO> getObligationsForCourse(String courseID) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			List<ObligationDTO> response = new ArrayList<ObligationDTO>();

			Optional<Course> course = courseRepo.findByCourseID(courseID);
			if (!course.isPresent())
				throw new RuntimeException("Course is not found. ID " + courseID);

			Set<Obligation> obligations = course.get().getObligations();

			for (Obligation o : obligations) {
				ObligationDTO obligat = modelMapper.map(o, ObligationDTO.class);
				obligat.setCourseName(o.getCourses().getName());
				response.add(obligat);
			}

			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error . Message:" + ex.getMessage());
		}

	}

	@Override
	public List<StudentDTO> getStudentsForCourse(String courseID) {
		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			List<StudentDTO> response = new ArrayList<StudentDTO>();

			Optional<Course> course = courseRepo.findByCourseID(courseID);
			if (!course.isPresent())
				throw new RuntimeException("Course is not found. ID " + courseID);

			Set<Registration> regs = course.get().getRegistratedStudents();

			for (Registration r : regs) {

				Student student = r.getStudent();

				response.add(modelMapper.map(student, StudentDTO.class));
			}

			return response;
		} catch (Exception ex) {
			throw new RuntimeException("Error. Message:" + ex.getMessage());
		}
	}

}
//create course for a teacher
/*
 * try { Optional<Teacher> teacher_who_added_course =
 * teacherRepository.findById(teacherId);
 * 
 * Course newCourse = courseService.createCourse(userCourse,
 * teacher_who_added_course.get());
 * 
 * Course saved = repository.save(newCourse);
 * 
 * CourseResponseModel createdCourse =
 * courseService.convertToDTOResponse(saved);
 * 
 * return new ResponseEntity<CourseResponseModel>(createdCourse,
 * HttpStatus.CREATED); } catch (IllegalArgumentException ex) { throw new
 * IllegalIdException(
 * "Illegal id for teacher or method body. Error in POST courses/teacher/" +
 * teacherId); } catch (Exception ex) { throw new
 * RuntimeException("Error in POST courses/teacher/\"+teacherId. Message:" +
 * ex.getMessage()); }
 */

//get all courses 
/*
 * try { Iterable<Course> foundCourses = repository.findAll();
 * 
 * List<CourseResponseModel> response =
 * courseService.convertToIterableDTOResponses(foundCourses);
 * 
 * return new ResponseEntity<List<CourseResponseModel>>(response,
 * HttpStatus.OK); } catch(Exception ex) { throw new
 * RuntimeException("Error in GET /courses. Message: "+ ex.getMessage()); }
 */

//get a specific course
/*
 * try {
 * 
 * Optional<Course> foundCourse = repository.findById(id);
 * 
 * CourseResponseModel response =
 * courseService.convertToDTOResponse(foundCourse.get());
 * 
 * return new ResponseEntity<CourseResponseModel>(response, HttpStatus.OK);
 * 
 * } catch (IllegalArgumentException ex) { throw new
 * RuntimeException("Error in GET /courses/"+id +". Message: "+
 * ex.getMessage()); } catch(Exception ex) { throw new
 * RuntimeException("Error in GET /courses/"+id +". Message: "+
 * ex.getMessage()); }
 */

//UPDATE COURSE
/*
 * try { Optional<Course> updateCourse = repository.findById(id);
 * 
 * Course foundCourse = updateCourse.get();
 * 
 * Course updated = courseService.updateCourse(foundCourse, desc);
 * 
 * Course saved = repository.save(updated);
 * 
 * CourseResponseModel response = courseService.convertToDTOResponse(saved);
 * 
 * return new ResponseEntity<CourseResponseModel>(response, HttpStatus.OK);
 * 
 * } catch (IllegalArgumentException ex) { throw new
 * RuntimeException("Error in PATCH /courses/"+id +". Message: "+
 * ex.getMessage()); } catch(Exception ex) { throw new
 * RuntimeException("Error in PATCH /courses/"+id +". Message: "+
 * ex.getMessage()); }
 */

//delete course 
/*
 * try { repository.deleteById(id);
 * 
 * return new ResponseEntity<CourseResponseModel>(HttpStatus.NO_CONTENT); }
 * catch (IllegalArgumentException ex) { throw new
 * RuntimeException("Error in DELETE /courses/"+id +". Message: "+
 * ex.getMessage()); } catch(Exception ex) { throw new
 * RuntimeException("Error in DELETE /courses/"+id +". Message: "+
 * ex.getMessage()); }
 */

//enrol student to course
/*
 * try { Course course = courseRepository.findById(idCourse).get();
 * 
 * //ovde mozes da proveris da vratis exception ako nije nasao nista
 * 
 * Student student = studentRepo.findById(idStudent).get();
 * 
 * Registration register_student_to_course = new Registration();
 * register_student_to_course.setCourses(course);
 * register_student_to_course.setStudent(student);
 * 
 * Registration savedReg = registrationRepo.save(register_student_to_course);
 * 
 * RegistrationDTO response =
 * courseService.convertToRegistrationDTOResponse(savedReg);
 * 
 * return new ResponseEntity<RegistrationDTO>(response, HttpStatus.OK); }
 * catch(Exception ex) { throw new
 * RuntimeException("Error in POST /students/+"+idStudent+"/course/"+idCourse
 * +". Message: "+ ex.getMessage()); }
 * 
 */