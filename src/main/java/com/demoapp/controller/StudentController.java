package com.demoapp.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.demoapp.exceptions.StudentNotFoundException;
import com.demoapp.model.Student;
import com.demoapp.repository.StudentRepository;
import com.demoapp.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {

	private final StudentRepository studentRepository;
	private StudentService studentService;

	public StudentController(StudentRepository studentRepository, StudentService studentService) {
		this.studentService = studentService;
		this.studentRepository = studentRepository;
	}

	@RequestMapping("/getAllStudents")
	public Iterable<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@RequestMapping("/addstudent")
	public ModelAndView showNewProductPage(Model model) {
		ModelAndView mav = new ModelAndView("addstudent");
		Student student = new Student();
		model.addAttribute("student", student);
		return mav;
	}

	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Student> studenList = studentService.listAll();
		model.addAttribute("studenList", studenList);
		return "index";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public RedirectView saveProduct(@ModelAttribute("student") Student student) {
		studentService.save(student);
		return new RedirectView("/");
	}

	@RequestMapping(value = "/edit/{id}", method = { RequestMethod.GET })
	public ModelAndView showEditProductPage(@PathVariable(name = "id") Long id) {
		ModelAndView mav = new ModelAndView("editstudent");
		Student student = studentService.get(id);
		mav.addObject("student", student);
		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET })
	public RedirectView deleteProduct(@PathVariable(name = "id") Long id) {
		studentService.delete(id);
		return new RedirectView("/");
	}

	@GetMapping("/getStudent/{id}")
	public Student getStudentById(@PathVariable Long id) {
		Student studentList = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		return studentList;
	}

	@PostMapping("/addStudents")
	public Student addStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	@PutMapping("{id}")
	public RedirectView updateStudent(@PathVariable Long id, @RequestBody Student student) {
		Student studentToUpdate = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		// studentToUpdate.setId(student.getId());
		studentToUpdate.setFirstName(student.getFirstName());
		studentToUpdate.setLastName(student.getLastName());
		studentToUpdate.setEmailId(student.getEmailId());
		studentToUpdate.setStdPwd(student.getStdPwd());
		studentToUpdate.setStdDeportment(student.getStdDeportment());
		studentToUpdate.setStudentDob(student.getStudentDob());
		studentToUpdate.setStudentAge(student.getStudentAge());
		studentToUpdate.setStdAddress(student.getStdAddress());
		studentToUpdate.setStdConductNo(student.getStdConductNo());
		studentRepository.save(studentToUpdate);
		return new RedirectView("/");
	}

	@DeleteMapping("{id}")
	public void deleteStudent(@PathVariable Long id) {
		studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		studentRepository.deleteById(id);
	}
}
