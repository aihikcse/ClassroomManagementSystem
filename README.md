# Classroom Management System

## Introduction

The Classroom Management System is a Java application designed to manage courses, students, and grades. The system allows users to perform various operations such as adding, updating, viewing, and deleting courses, students, and grades.

## Project Structure

ClassroomManagementSystem/
├── src/
│   ├── com/
│   │   ├── classroom/
│   │   │   ├── main/
│   │   │   │   └── Main.java
│   │   │   ├── manager/
│   │   │   │   ├── CourseManager.java
│   │   │   │   ├── GradeManager.java
│   │   │   │   └── StudentManager.java
│   │   │   ├── model/
│   │   │   │   ├── Course.java
│   │   │   │   ├── Grade.java
│   │   │   │   └── Student.java
│   │   │   └── util/
│   │   │       └── DatabaseConnection.java
├── README.md
└── Database.sql

## Pre-requisites

- Java SE Development Kit (JDK) 17
- MySQL Database

## Setup Instructions

1. **Clone the Repository**

   Clone the repository to your local machine using the following command:

   git clone <https://github.com/aihikcse/ClassroomManagementSystem.git>

2. **Configure the Database**

   - Ensure MySQL is installed and running on your machine.
   - Create a database named `classroom_management`.
   - Use the SQL script in `Database.sql` to create the necessary tables:

3. **Update Database Configuration**

   Open the `DatabaseConnection.java` file located in the `src/com/classroom/util/` directory. Update the database URL, user, and password with your MySQL database credentials:

4. **Compile and Run the Application**

   - Open a terminal or command prompt.      javac com/classroom/main/Main.java

   - Run the application:        java com.classroom.main.Main

## Usage Instructions

1. **Main Menu**

   - When you run the application, you will be presented with the main menu with options for Course Management, Student Management, Grade Management,Calculate GPA and Exit.

2. **Course Management**

   - Add a new course
   - View all courses
   - View course by ID
   - Update course information
   - Delete a course

3. **Student Management**

   - Register a new student
   - View all students
   - View student by ID
   - Update student information
   - Delete a student

4. **Grade Management**

   - Assign a grade to a student for a course
   - View grades for a student
   - Update grade information
   - Remove a grade

5. **Calculate GPA**

   - Calculate GPA of a student considering all courses he/she has taken

## Authors

- Aihik Makur (<amakur.bku@gmail.com>)
