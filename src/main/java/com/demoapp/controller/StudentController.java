package com.demoapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.demoapp.exceptions.StudentNotFoundException;
import com.demoapp.model.Student;
import com.demoapp.repository.StudentRepository;
import com.demoapp.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {

	private static String UPLOADED_FOLDER = "F:/temp/";
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
	
	@GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

	@SuppressWarnings("resource")
	@RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public RedirectView singleFileUpload(@RequestParam("file") MultipartFile file,
    		Model model) {

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            List<Student> resultSet = studentService.listAll();
            XSSFWorkbook workbook = new XSSFWorkbook(); 
            XSSFSheet spreadsheet = workbook.createSheet("Student Info");
            
            XSSFRow row = spreadsheet.createRow(1);
            XSSFCell cell;

            cell = row.createCell(1);
            cell.setCellValue("Student Id");
            cell = row.createCell(2);
            cell.setCellValue("First Name");
            cell = row.createCell(3);
            cell.setCellValue("Last Name");
            cell = row.createCell(4);
            cell.setCellValue("EmailId");
            cell = row.createCell(5);
            cell.setCellValue("Deportment");
            cell = row.createCell(6);
            cell.setCellValue("Date Of Birth");
            cell = row.createCell(7);
            cell.setCellValue("Age");
            cell = row.createCell(8);
            cell.setCellValue("Address");
            cell = row.createCell(9);
            cell.setCellValue("Conduct No");
            int i = 2;
            int b = i-1;
           // while(resultSet.iterator().next() != null) {
            	Student std = resultSet.get(b);
            for(b=1; b < i; b++) {
               row = spreadsheet.createRow(i);
               cell = row.createCell(1);
               cell.setCellValue(std.getId());
               cell = row.createCell(2);
               cell.setCellValue(std.getFirstName());
               cell = row.createCell(3);
               cell.setCellValue(std.getLastName());
               cell = row.createCell(4);
               cell.setCellValue(std.getEmailId());
               cell = row.createCell(5);
               cell.setCellValue(std.getStdDeportment());
               cell = row.createCell(6);
               cell.setCellValue(std.getStudentDob());
               cell = row.createCell(7);
               cell.setCellValue(std.getStudentAge());
               cell = row.createCell(8);
               cell.setCellValue(std.getStdAddress());
               cell = row.createCell(9);
               cell.setCellValue(std.getStdConductNo());
               i++;
            }

            FileOutputStream out = new FileOutputStream(new File("studentinfo.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("studentinfo.xlsx written successfully");
         

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new RedirectView("/");
    }

}
