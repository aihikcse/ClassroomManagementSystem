package com.classroom.model;

public class Grade {
	
	//	variables
	private int gradeId;
    private int courseId;
    private int studentId;
    private double grade;
    
    // getters and setters
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	//	constructors
	public Grade(int gradeId, int courseId, int studentId, double grade) {
		super();
		this.gradeId = gradeId;
		this.courseId = courseId;
		this.studentId = studentId;
		this.grade = grade;
	}
	public Grade() {
	
	}

        
}
