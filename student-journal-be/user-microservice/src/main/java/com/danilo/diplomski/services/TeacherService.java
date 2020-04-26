package com.danilo.diplomski.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.danilo.diplomski.models.DTO.TeacherDTO;

public interface TeacherService extends UserDetailsService{

	TeacherDTO createTeacher(TeacherDTO teacher);

	TeacherDTO updateTeacher(String teacher, TeacherDTO teacher2);
	
	boolean deleteByTeacherID(String str);
	
	TeacherDTO findByTeacherID(String str);
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	
	//STUDENTDTO
	TeacherDTO getUserDetailsByEmail(String email);
}
