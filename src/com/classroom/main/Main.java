package com.classroom.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.classroom.manager.CourseManager;
import com.classroom.manager.GradeManager;
import com.classroom.manager.StudentManager;
import com.classroom.model.Course;
import com.classroom.model.Grade;
import com.classroom.model.Student;
import com.classroom.util.DatabaseConnection;

public class Main {
	
	private static final Scanner scanner = new Scanner(System.in);
    private static final CourseManager courseManager = new CourseManager();
    private static final StudentManager studentManager = new StudentManager();
    private static final GradeManager gradeManager = new GradeManager();

    public static void main(String[] args) {
    	
    // start of main function
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            System.out.println("Connection successful!");
            boolean exit = false;
            while (!exit) {
            	
            	// Menu for Classroom Management System
                System.out.println("\nWelcome to Classroom Management System");
                System.out.println("1. Course Management");
                System.out.println("2. Student Management");
                System.out.println("3. Grade Management");
                System.out.println("4. Calculate GPA");
                System.out.println("5. Exit\n");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                System.out.println();

                switch (choice) {
                    case 1:
                        courseManagement();
                        break;
                    case 2:
                        studentManagement();
                        break;
                    case 3:
                        gradeManagement();
                        break;
                    case 4:
                    	calculateGPA();
                    	break;
                    case 5:
                        exit = true;
                        System.out.println("Exiting application...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option (1-5).");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
   
 // start of course management
    
    private static void courseManagement() {
        boolean back = false;
        while (!back) {
        	
        	// Menu for Course Management
            System.out.println("\nCourse Management Menu:");
            System.out.println("1. Add a new course");
            System.out.println("2. View all courses");
            System.out.println("3. View course by ID");
            System.out.println("4. Update course information");
            System.out.println("5. Delete a course");
            System.out.println("6. Back to main menu\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
    
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    viewCourseById();
                    break;
                case 4:
                    updateCourse();
                    break;
                case 5:
                    deleteCourse();
                    break;
                case 6:
                    back = true;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option (1-6).");
            }
        }
    }

    // function to add course
    private static void addCourse() {
        System.out.println("Enter course details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Instructor: ");
        String instructor = scanner.nextLine();
        System.out.print("Schedule: ");
        String schedule = scanner.nextLine();
        System.out.print("Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Course course = new Course();
        course.setTitle(title);
        course.setInstructor(instructor);
        course.setSchedule(schedule);
        course.setCredits(credits);

        courseManager.addCourse(course);
    }

    // function to view all courses 
    private static void viewAllCourses() {
        List<Course> courses = courseManager.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("List of courses:");
            System.out.println("----------------------------------------------");
            System.out.println("CourseId  Title  Instructor  Schedule  Credits");
            System.out.println("----------------------------------------------");
            for (Course course : courses) {
                System.out.println(course.getCourseId() + ".        " + course.getTitle() + "   " + course.getInstructor()+"          "+ course.getSchedule()+ "         " + course.getCredits());
            }
            System.out.println("----------------------------------------------");
        }
    }

    // function to view a course by its Id
    private static void viewCourseById() {
        System.out.print("Enter course ID to view details: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Course course = courseManager.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
        } else {
            System.out.println("Course details:");
            System.out.println("----------------------------------------------");
            System.out.println("CourseId  Title  Instructor  Schedule  Credits");
            System.out.println("----------------------------------------------");
            System.out.println(course.getCourseId() + ".        " + course.getTitle() + "   " + course.getInstructor()+"          "+ course.getSchedule()+ "         " + course.getCredits());
            System.out.println("----------------------------------------------");
        }
    }

    // function to update a course by its Id
    private static void updateCourse() {
        viewAllCourses();
        System.out.print("Enter course ID to update: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Course course = courseManager.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.println("Enter new details for course ID " + courseId + ":");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Instructor: ");
        String instructor = scanner.nextLine();
        System.out.print("Schedule: ");
        String schedule = scanner.nextLine();
        System.out.print("Credits: ");
        int credits = scanner.nextInt();

        course.setTitle(title);
        course.setInstructor(instructor);
        course.setSchedule(schedule);
        course.setCredits(credits);

        courseManager.updateCourse(course);
    }

    // function to delete a course by its Id
    private static void deleteCourse() {
        viewAllCourses();
        System.out.print("Enter course ID to delete: ");
        int courseId = scanner.nextInt();

        Course course = courseManager.getCourseById(courseId);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        courseManager.deleteCourse(courseId);
    }

// end of course management
    
// start of student management
    
    private static void studentManagement() {
        boolean back = false;
        while (!back) {
        	
        	// Menu for Student Management
            System.out.println("\nStudent Management Menu:");
            System.out.println("1. Register a new student");
            System.out.println("2. View all students");
            System.out.println("3. View student by ID");
            System.out.println("4. Update student information");
            System.out.println("5. Delete a student");
            System.out.println("6. Back to main menu\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    back = true;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option (1-6).");
            }
        }
    }

    // function to add a Student record by StudentId
    private static void addStudent() {
        System.out.println("Enter student details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        

        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setAddress(address);

        studentManager.addStudent(student);
    }

    private static void viewAllStudents() {
        List<Student> students = studentManager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("List of students:");
            System.out.println("--------------------------------------------------------------");
            System.out.println("StudentId  Name    Email      Phone No.      Address");
            System.out.println("--------------------------------------------------------------");
            for (Student student : students) {
                System.out.println(student.getStudentId() + ".\t   " + student.getName() + "   " + student.getEmail()+"  "+student.getPhoneNumber()+"       "+student.getAddress());
            }
            System.out.println("--------------------------------------------------------------");
        }
    }

    // function to view a Student record by StudentId
    private static void viewStudentById() {
        System.out.print("Enter student ID to view details: ");
        int studentId = scanner.nextInt();
        
        Student student = studentManager.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println("Student details:");
            System.out.println("--------------------------------------------------------------");
            System.out.println("StudentId  Name    Email      Phone No.      Address");
            System.out.println("--------------------------------------------------------------");
            System.out.println(student.getStudentId() + ".\t  " + student.getName() + "   " + student.getEmail()+"  "+student.getPhoneNumber()+"       "+student.getAddress());       
            System.out.println("--------------------------------------------------------------");
        }
    }

    // function to update a Student record by StudentId
    private static void updateStudent() {
        viewAllStudents();
        System.out.print("Enter student ID to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        Student student = studentManager.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Enter new details for student ID " + studentId + ":");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();

        student.setName(name);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setAddress(address);

        studentManager.updateStudent(student);
        
    }

    // function to delete a Student record by StudentId
    private static void deleteStudent() {
        viewAllStudents();
        System.out.print("Enter student ID to delete: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        Student student = studentManager.getStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        studentManager.deleteStudent(studentId);
    }
    
    // end of student management
    
    // start of grade management

    private static void gradeManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("\nGrade Management Menu:");
            System.out.println("1. Assign a grade to a student for a course");
            System.out.println("2. View grades for a student");
            System.out.println("3. Update grade information");
            System.out.println("4. Remove a grade");
            System.out.println("5. Back to main menu\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    assignGrade();
                    break;
                case 2:
                    viewGrades();
                    break;
                case 3:
                    updateGrade();
                    break;
                case 4:
                    removeGrade();
                    break;
                case 5:
                    back = true;
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option (1-6).");
            }
        }
    }

    // function to assign Grade to a student by StudentId and CourseId
    private static void assignGrade() {
        System.out.println("Enter grade details:");
        System.out.print("Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Grade: ");
        Double grade = scanner.nextDouble();

        Grade newGrade = new Grade();
        newGrade.setStudentId(studentId);
        newGrade.setCourseId(courseId);
        newGrade.setGrade(grade);

        gradeManager.assignGrade(newGrade);
       
    }

    // function to view Grade of a student by StudentId
    private static void viewGrades() {
        System.out.print("Enter student ID to view grades: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        List<Grade> grades = gradeManager.getGradesByStudentId(studentId);
        if (grades.isEmpty()) {
            System.out.println("No grades found for student ID: " + studentId);
            return;
        } else {
            System.out.println("Grades for student ID " + studentId + ":");
            System.out.println("----------------------------------------------");
            System.out.println("GradeId  CourseId  Grade");
            System.out.println("----------------------------------------------");
            for (Grade grade : grades) {
                System.out.println(grade.getGradeId()+".        " + grade.getCourseId() + "       " + grade.getGrade());
            }
            System.out.println("----------------------------------------------");
        }
    }

    // function to update Grade of a student by GradeId
    private static void updateGrade() {
        viewGrades();
        System.out.print("Enter grade ID to update: ");
        int gradeId = scanner.nextInt();
        scanner.nextLine();

        Grade grade = new Grade();
        grade.setGradeId(gradeId);

        System.out.print("Enter new grade: ");
        Double newGrade = scanner.nextDouble();

        grade.setGrade(newGrade);
        gradeManager.updateGrade(grade);
        
    }

    // function to remove Grade of a student by GradeId
    private static void removeGrade() {
        viewGrades();
        System.out.print("Enter grade ID to remove: ");
        int gradeId = scanner.nextInt();
        scanner.nextLine();

        gradeManager.deleteGrade(gradeId); 
    }
    // end of grade management
    
    // start of GPA calculate function
    private static void calculateGPA() {
        System.out.print("Enter student ID to calculate GPA: ");
        int studentId = scanner.nextInt();
        if (!gradeManager.studentExists(studentId)) {
            System.out.println("Student ID does not exist.");
        } else if (!gradeManager.studentHasGrades(studentId)) {
            System.out.println("Grades for student ID:"+studentId+" does not exist.");
        } else {
            double gpa = gradeManager.calculateGPA(studentId);
            System.out.println("The GPA for student ID " + studentId + " is: " + gpa);
        }
    }
    // end of GPA calculate function
    
 // end of main function
}
