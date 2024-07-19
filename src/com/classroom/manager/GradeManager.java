package com.classroom.manager;

import com.classroom.model.Grade;
import com.classroom.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeManager {
    private Connection connection;

    public GradeManager() {
    	try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  Assigns a grade to a student for a specific course
    public void assignGrade(Grade grade) {
    	
        
        if (!courseExists(grade.getCourseId())) {
            System.out.println("Error: Course ID " + grade.getCourseId() + " does not exist.");
            return;
        }

        if (!studentExists(grade.getStudentId())) {
            System.out.println("Error: Student ID " + grade.getStudentId() + " does not exist.");
            return;
        }

        String query = "INSERT INTO grades (course_id, student_id, grade) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
        	statement.setInt(1, grade.getCourseId());
        	statement.setInt(2, grade.getStudentId());
        	statement.setDouble(3, grade.getGrade());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Assigning grade failed, no rows affected.");
            } else {
                System.out.println("Grade assigned successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if the course exists
    private boolean courseExists(int courseId) {
        String query = "SELECT 1 FROM course WHERE course_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if the student exists
    public boolean studentExists(int studentId) {
        String query = "SELECT 1 FROM student WHERE student_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //    Retrieves all grades for a specific student
    public List<Grade> getGradesByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Grade grade = new Grade();
                    grade.setGradeId(resultSet.getInt("grade_id"));
                    grade.setCourseId(resultSet.getInt("course_id"));
                    grade.setStudentId(resultSet.getInt("student_id"));
                    grade.setGrade(resultSet.getDouble("grade"));

                    grades.add(grade);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return grades;
    }

    //    Updates the grade information of a student for a specific course
    public void updateGrade(Grade grade) {
        String sql = "UPDATE grades SET grade = ? WHERE grade_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, grade.getGrade());
            statement.setInt(2, grade.getGradeId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating grade failed, no rows affected.");
            }
            else
            	System.out.println("Grade updated successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //    Removes a grade from the database
    public void deleteGrade(int gradeId) {
        String sql = "DELETE FROM grades WHERE grade_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, gradeId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Removing grade failed, no rows affected.");
            }
            else
            	System.out.println("Grade removed successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Check if the student has grades
    public boolean studentHasGrades(int studentId) {
        String query = "SELECT COUNT(*) FROM grades WHERE student_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //    Calculates the GPA for a student based on their grades
    public double calculateGPA(int studentId) {
        String query = "SELECT grade FROM grades WHERE student_id = ?";
        double totalGrades = 0;
        int gradeCount = 0;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    totalGrades += rs.getDouble("grade");
                    gradeCount++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gradeCount == 0 ? 0 : totalGrades / gradeCount;
    }
}

