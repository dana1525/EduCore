package com.elearning.service;

// Adding new course
// TreeMap, sorts automatically

import com.elearning.enums.Difficulty;
import com.elearning.model.Course;
import com.elearning.model.CourseModule;
import com.elearning.model.Instructor;
import com.elearning.repository.CourseModuleRepository;
import com.elearning.repository.CourseRepository;

import java.sql.SQLException;
import java.util.List;

public class CourseService {
    // Existing Comparator in Java
//    private TreeMap<String, Course> courses = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
//    private static int nextId;
    private CourseRepository repository = new CourseRepository();
    private CourseModuleRepository moduleRepository = new CourseModuleRepository();

    public Course addCourse(String title, String description, Instructor instructor, Difficulty difficulty) throws SQLException {
        try {
            if (repository.findByTitle(title) != null) {
                throw new IllegalArgumentException("A couse with this title already exists: " + title);
            }
            return repository.save(new Course(0, title, description, instructor, difficulty));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listAllCourses() {
        try {
            List<Course> courses = repository.findAll();
            courses.forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list all courses" + e);
        }
    }

    public Course findById(int id) {
        try {
            return repository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CourseModule addModuleToCourse(int courseId, String moduleTitle, int position) throws SQLException {
        Course course = findById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found:" + courseId);
        }
        CourseModule module = new CourseModule(moduleTitle, position, courseId);
//        course.addModule(module);
        return moduleRepository.save(module);
    }

    public void updateModule(CourseModule module) throws SQLException {
        moduleRepository.update(module);
    }

    public List<CourseModule> getModulesByCourse(int courseId) throws SQLException {
        return moduleRepository.findByCourseId(courseId);
    }
}
