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
    private CourseRepository courseRepository = new CourseRepository();
    private CourseModuleRepository moduleRepository = new CourseModuleRepository();

    public Course addCourse(String title, String description, Instructor instructor, Difficulty difficulty) {
        try {
            if (courseRepository.findByTitle(title) != null) {
                throw new IllegalArgumentException("A course with this title already exists: " + title);
            }
            return courseRepository.save(new Course(0, title, description, instructor, difficulty));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Course> getAllCourses() {
        try {
            List<Course> courses = courseRepository.findAll();
            for (Course course : courses) {
                course.setModules(moduleRepository.findByCourseId(course.getId()));
            }
            return courses;
        } catch (SQLException e) {
            throw new RuntimeException("Error listing courses.", e);
        }
    }

    public Course findById(int id) {
        try {
            return courseRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public CourseModule getModuleById(int id) {
        try {
            return moduleRepository.findById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Error finding module.", e);
        }
    }

    public CourseModule addModuleToCourse(int courseId, String moduleTitle, int position) throws SQLException {
        Course course = findById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found:" + courseId);
        }
        CourseModule module = new CourseModule(moduleTitle, position, courseId);
        return moduleRepository.save(module);
    }

    public void updateModule(CourseModule module) throws SQLException {
        moduleRepository.update(module);
    }

    public List<CourseModule> getModulesByCourse(int courseId) {
        try {
            return moduleRepository.findByCourseId(courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Error listing modules.", e);
        }
    }
}
