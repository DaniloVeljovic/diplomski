package com.danilo.diplomski.repositories;


import javax.transaction.Transactional;

import com.danilo.diplomski.models.data.Student;
@Transactional
public interface StudentRepository extends UserBaseRepository<Student> {


}
