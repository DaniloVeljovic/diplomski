package com.danilo.diplomski.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import com.danilo.diplomski.models.data.Teacher;

@Transactional
public interface TeacherRepository extends UserBaseRepository<Teacher> {

	Optional<Teacher> findByUserID(String teacherID);

}
