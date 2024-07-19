package com.classroom.manager;

import com.classroom.model.Student;
import com.classroom.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private Connection connection;

    public StudentManager() {
    	try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    Registers a new student in the database
    public void addStudent(Student student) {
        String sql = "INSERT INTO student (name, email, phone_number, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) 
        {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getPhoneNumber());
            statement.setString(4, student.getAddress());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Creating student failed, no rows affected.");           
            else 
                System.out.println("Student added successfully.");
           

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                    student.setStudentId(generatedKeys.getInt(1));
                else
                    throw new SQLException("Creating student failed, no ID obtained.");
               
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
    }

    //    Retrieves all students from the database
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setName(resultSet.getString("name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhoneNumber(resultSet.getString("phone_number"));
                student.setAddress(resultSet.getString("address"));

                students.add(student);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return students;
    }

    //    Retrieves a specific student by their ID
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM student WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Student student = new Student();
                    student.setStudentId(resultSet.getInt("student_id"));
                    student.setName(resultSet.getString("name"));
                    student.setEmail(resultSet.getString("email"));
                    student.setPhoneNumber(resultSet.getString("phone_number"));
                    student.setAddress(resultSet.getString("address"));
                    return student;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //    Updates the information of an existing student
    public void updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, email = ?, phone_number = ?, address = ? WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getPhoneNumber());
            statement.setString(4, student.getAddress());
            statement.setInt(5, student.getStudentId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating student failed, no rows affected.");
            }
            else 
                System.out.println("Student updated successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //     Deletes a student from the database
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM student WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting student failed, no rows affected.");
            }
            else {
                System.out.println("Student deleted successfully.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Cannot delete student with ID " + studentId + " as they have associated grades.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
