package com.demoapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String stdPwd;
	private String stdDeportment;
	private String studentDob;
	private int studentAge;
	private String stdAddress;
	private int stdConductNo;

	public Student() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getStdPwd() {
		return stdPwd;
	}

	public void setStdPwd(String stdPwd) {
		this.stdPwd = stdPwd;
	}

	public String getStdDeportment() {
		return stdDeportment;
	}

	public void setStdDeportment(String stdDeportment) {
		this.stdDeportment = stdDeportment;
	}

	public String getStudentDob() {
		return studentDob;
	}

	public void setStudentDob(String studentDob) {
		this.studentDob = studentDob;
	}

	public int getStudentAge() {
		return studentAge;
	}

	public void setStudentAge(int studentAge) {
		this.studentAge = studentAge;
	}

	public String getStdAddress() {
		return stdAddress;
	}

	public void setStdAddress(String stdAddress) {
		this.stdAddress = stdAddress;
	}

	public int getStdConductNo() {
		return stdConductNo;
	}

	public void setStdConductNo(int stdConductNo) {
		this.stdConductNo = stdConductNo;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName= " + lastName + " , emailId=" + emailId
				+ ", stdDeportment=" + stdDeportment + ", studentDob=" + studentDob + ", studentAge=" + studentAge
				+ ", stdAddress=" + stdAddress + ", stdConductNo=" + stdConductNo + "]";
	}

}
