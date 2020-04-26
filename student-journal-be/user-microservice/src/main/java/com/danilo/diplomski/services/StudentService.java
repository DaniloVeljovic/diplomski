package com.danilo.diplomski.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.danilo.diplomski.models.DTO.StudentDTO;

public interface StudentService extends UserDetailsService {

	StudentDTO createStudent(StudentDTO student);
	
	StudentDTO updateStudent(String id, StudentDTO student);
	
	StudentDTO findByStudentID(String id);
	
	boolean deleteStudent(String id);
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	//STUDENTDTO
	StudentDTO getUserDetailsByEmail(String email);
}
