package com.danilo.diplomski.repositories;

import javax.transaction.Transactional;

import com.danilo.diplomski.models.data.Teacher;

@Transactional
public interface TeacherRepository extends UserBaseRepository<Teacher> {

}
