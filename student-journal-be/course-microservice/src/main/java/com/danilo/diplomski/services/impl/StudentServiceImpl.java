package com.danilo.diplomski.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danilo.diplomski.exceptions.IllegalIdException;
import com.danilo.diplomski.exceptions.StudentNotEnroledException;
import com.danilo.diplomski.models.RegistrationStatus;
import com.danilo.diplomski.models.DTO.CourseDTO;
import com.danilo.diplomski.models.DTO.ObligationDTO;
import com.danilo.diplomski.models.DTO.RegistrationDTO;
import com.danilo.diplomski.models.DTO.StudentDTO;
import com.danilo.diplomski.models.data.Course;
import com.danilo.diplomski.models.data.Obligation;
import com.danilo.diplomski.models.data.Student;
import com.danilo.diplomski.models.data.junctiontables.Registration;
import com.danilo.diplomski.repositories.CourseRepository;
import com.danilo.diplomski.repositories.RegistrationRepository;
import com.danilo.diplomski.repositories.StudentRepository;

import com.danilo.diplomski.services.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepo;
	private RegistrationRepository registrationRepo;
	private CourseRepository courseRepo;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepo, RegistrationRepository registrationRepo,
			CourseRepository courseRepo) {
		this.studentRepo = studentRepo;
		this.registrationRepo = registrationRepo;
		this.courseRepo = courseRepo;
	}

	@Override
	public StudentDTO createStudent(StudentDTO studentToCreate) {

		try {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Student student = modelMapper.map(studentToCreate, Student.class);

			Student saved = studentRepo.save(student);

			StudentDTO response = modelMapper.map(saved, StudentDTO.class);

			return response;
		} catch (Exception e) {
			throw new RuntimeException("Error in POST /students " + e.getMessage());
		}
	}

	@Override
	public List<CourseDTO> findAllCoursesForStudent(String studentID) {
		try {

			List<CourseDTO> response = new ArrayList<CourseDTO>();
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Optional<Student> student = studentRepo.findByUserID(studentID);

			if (!student.isPresent())
				throw new IllegalIdException("Student with ID" + studentID + "not found");

			Set<Registration> courses = student.get().getRegistrations();

			for (Registration r : courses) {
				Course c = r.getCourses();
				CourseDTO cour = modelMapper.map(c, CourseDTO.class);
				cour.setTeacherName(c.getTeacher().getName() + " " + c.getTeacher().getSurname());
				response.add(cour);
			}

			return response;

		} catch (Exception e) {
			throw new RuntimeException("Error in POST /students " + e.getMessage());
		}
	}

	@Override
	public List<ObligationDTO> findAllObligationsForDate(String studentID, LocalDateTime date) {
		try {
			Student student = studentRepo.findByUserID(studentID).get();

			ModelMapper modelMapper = new ModelMapper();
			modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Set<Registration> regs = student.getRegistrations();

			List<ObligationDTO> returnObligat = new ArrayList<ObligationDTO>();

			for (Registration r : regs) { // iskoristi hes mapu ovde, bice brze
				Set<Obligation> oblig = r.getCourses().getObligations();

				for (Obligation o : oblig) {
					// if (o.getDate().compareTo(date) == 0) {
					if ((o.getDate().getDayOfMonth() == date.getDayOfMonth())
							&& (o.getDate().getMonth().equals(date.getMonth()))
							&& (o.getDate().getYear() == date.getYear())) {
						ObligationDTO obligat = modelMapper.map(o, ObligationDTO.class);
						obligat.setCourseName(o.getCourses().getName());
						returnObligat.add(obligat);
					}

				}
			}

			return returnObligat;
		} catch (Exception e) {
			throw new RuntimeException("Error in POST /students " + e.getMessage());
		}
	}

	@Override
	public Student findByUserID(String studentID) {

		Optional<Student> student = studentRepo.findByUserID(studentID);

		if (!student.isPresent())
			throw new IllegalIdException("Student with ID" + studentID + "not found");

		return student.get();

	}

	@Override
	public void deleteRegistration(RegistrationDTO resFail) {

		Optional<Student> found = studentRepo.findByUserID(resFail.getUserID());
		
		if (!found.isPresent())
			throw new IllegalIdException("Student with not found");
		
		Iterable<Registration> regs = found.get().getRegistrations();
		Registration resF = null;
		for (Registration r : regs) {
			if (r.getCourses().getName().equals(resFail.getCourseID())) {
				resF = r;
			}
		}

		resF.setStatus(RegistrationStatus.REJECTED);
		registrationRepo.save(resF);
	}

	@Override
	public void confirmRegistration(RegistrationDTO resSuccess) {

		Optional<Student> found = studentRepo.findByUserID(resSuccess.getUserID());
		// Optional<Course> course =
		// courseRepo.findByCourseID(resSuccess.getCourseID());

		Student s = found.get();
		
		Set<Registration> regs = s.getRegistrations();
		Registration resF = null;
		for (Registration r : regs) {
			if (r.getCourses().getName().equals(resSuccess.getCourseID())) {
				resF = r;
			}
		}

		resF.setStatus(RegistrationStatus.ACCEPTED);
		registrationRepo.save(resF);
	}

	@Override
	public void checkIfEnroled(String userID, String courseID) {
		
		Optional<Student> student = studentRepo.findByUserID(userID);
		
		if (!student.isPresent())
			throw new IllegalIdException("Student with ID" + userID + "not found");
		
		Optional<Course> course = courseRepo.findByCourseID(courseID);
		
		if(!course.isPresent())
			throw new  IllegalIdException("Course not found. Id: "+courseID);
		
		Set<Registration> regs = student.get().getRegistrations();
		
		boolean found = false;
		for(Registration r : regs)
		{
			if(r.getCourses().equals(course.get()) && (r.getStatus().equals(RegistrationStatus.ACCEPTED)))
			{
				found = true;
			}
		}
		
		if(found==false)
			throw new StudentNotEnroledException("Student with ID " + userID + "is not registered to the course.");
		
	}

}
//create student
/*
 * try { Student studentToSave = studentRepo.save(student);
 * 
 * return new ResponseEntity<Student>(studentToSave, HttpStatus.OK); } catch
 * (Exception ex) { throw new
 * RuntimeException("Error in POST /students . Message: " + ex.getMessage()); }
 */

//get all courses in which the student enrolled
/*
 * try { Iterable<Registration> registratedCoursesForStudent =
 * registrationRepo.findByStudentId(idStudent); List<Course> foundCourses = new
 * ArrayList<Course>();
 * 
 * for (Registration r : registratedCoursesForStudent) {
 * foundCourses.add(r.getCourses()); }
 * 
 * List<CourseResponseModel> response =
 * courseService.convertToIterableDTOResponses(foundCourses);
 * 
 * return new ResponseEntity<List<CourseResponseModel>>(response,
 * HttpStatus.OK); } catch (Exception ex) { throw new RuntimeException(
 * "Error in POST /students/+" + idStudent + "/course/" + idCourse +
 * ". Message: " + ex.getMessage()); }
 */

// retrieve all obligations for a student for a specific date
/*
 * try { Student student = studentRepo.findById(id).get();
 * 
 * Set<Registration> regs = student.getRegistrations();
 * 
 * List<ObligationDTO> returnObligat = new ArrayList<ObligationDTO>();
 * 
 * for (Registration r : regs) { // iskoristi hes mapu ovde, bice brze
 * Set<Obligation> oblig = r.getCourses().getObligations();
 * 
 * for (Obligation o : oblig) { if (o.getDate().compareTo(date) == 0) {
 * 
 * // OVO POMERI U OBLIGATION SERVIS
 * 
 * ObligationDTO tempObligation = new ObligationDTO();
 * tempObligation.setDate(o.getDate()); tempObligation.setId(o.getId());
 * tempObligation.setDescription(o.getDescription());
 * tempObligation.setType(o.getType()); returnObligat.add(tempObligation); }
 * 
 * } }
 * 
 * return new ResponseEntity<List<ObligationDTO>>(returnObligat, HttpStatus.OK);
 * 
 * } catch (Exception ex) { throw new
 * RuntimeException("Error in GET /students//{id}/date/{date} " + "Message: " +
 * ex.getMessage()); }
 */