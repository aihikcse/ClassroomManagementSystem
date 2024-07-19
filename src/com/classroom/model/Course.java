package com.classroom.model;

public class Course {
	
	//	variables
	private int courseId;
    private String title;
    private String instructor;
    private String schedule;
    private int credits;
    
    // getters and setters
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	//	constructors
	public Course(int courseId, String title, String instructor, String schedule, int credits) {
		super();
		this.courseId = courseId;
		this.title = title;
		this.instructor = instructor;
		this.schedule = schedule;
		this.credits = credits;
	}
	public Course() {
		
	}
	
    
	
    
}
