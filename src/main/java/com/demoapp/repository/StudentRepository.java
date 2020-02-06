package com.demoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demoapp.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
