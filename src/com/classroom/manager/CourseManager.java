package com.classroom.manager;

import com.classroom.model.Course;
import com.classroom.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private Connection connection;

    public CourseManager() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Adds a new course to the database
    public void addCourse(Course course) {
        String sql = "INSERT INTO Course (title, instructor, schedule, credits) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getInstructor());
            statement.setString(3, course.getSchedule());
            statement.setInt(4, course.getCredits());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Adding course failed, no rows affected.");
            } else {
                System.out.println("Course added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    Retrieves all courses from the database.
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setTitle(resultSet.getString("title"));
                course.setInstructor(resultSet.getString("instructor"));
                course.setSchedule(resultSet.getString("schedule"));
                course.setCredits(resultSet.getInt("credits"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    //    Retrieves a specific course by its ID
    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM Course WHERE course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Course course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setTitle(resultSet.getString("title"));
                course.setInstructor(resultSet.getString("instructor"));
                course.setSchedule(resultSet.getString("schedule"));
                course.setCredits(resultSet.getInt("credits"));
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    Updates the information of an existing course
    public void updateCourse(Course course) {
        String sql = "UPDATE Course SET title = ?, instructor = ?, schedule = ?, credits = ? WHERE course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getTitle());
            statement.setString(2, course.getInstructor());
            statement.setString(3, course.getSchedule());
            statement.setInt(4, course.getCredits());
            statement.setInt(5, course.getCourseId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating course failed, no rows affected.");
            } else {
                System.out.println("Course updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();        
        }
    }

    //    Deletes a course from the database, ensuring foreign key constraints are handled
    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM Course WHERE course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting course failed, no rows affected.");
            } else {
                System.out.println("Course deleted successfully.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Cannot delete Course with ID " + courseId + " as it has associated grades.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
