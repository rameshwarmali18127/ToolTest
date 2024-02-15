package com.atlasCopco.ToolTest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="student_details")
public class StudentEntity {
   
	@Id
	private String studentId;
	private String studentName;
	private Integer mathsMarks;
	private Integer englishMarks;
	private Integer scienceMarks;
	
	
	
	
	public StudentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public StudentEntity(String studentId, String studentName, Integer mathsMarks, Integer englishMarks,
			Integer scienceMarks) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.mathsMarks = mathsMarks;
		this.englishMarks = englishMarks;
		this.scienceMarks = scienceMarks;
	}
	
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getMathsMarks() {
		return mathsMarks;
	}
	public void setMathsMarks(Integer mathsMarks) {
		this.mathsMarks = mathsMarks;
	}
	public Integer getEnglishMarks() {
		return englishMarks;
	}
	public void setEnglishMarks(Integer englishMarks) {
		this.englishMarks = englishMarks;
	}
	public Integer getScienceMarks() {
		return scienceMarks;
	}
	public void setScienceMarks(Integer scienceMarks) {
		this.scienceMarks = scienceMarks;
	}
 




	@Override
	public String toString() {
		return "StudentEntity [studentId=" + studentId + ", studentName=" + studentName + ", mathsMarks=" + mathsMarks
				+ ", englishMarks=" + englishMarks + ", scienceMarks=" + scienceMarks + "]";
	}
	
	
	
	
	 

}
