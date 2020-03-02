package com.demoapp.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demoapp.model.Student;
import com.demoapp.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public List<Student> listAll() {
		return (List<Student>) studentRepository.findAll();
	}

	public void save(Student student) {
		studentRepository.save(student);
	}

	public Student get(long id) {
		return studentRepository.findById(id).get();
	}

	public void delete(long id) {
		studentRepository.deleteById(id);
	}
	
}
