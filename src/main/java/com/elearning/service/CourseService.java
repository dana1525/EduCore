package com.elearning.service;

// Adding new course
// TreeMap, sorts automatically

import com.elearning.enums.Difficulty;
import com.elearning.model.Course;
import com.elearning.model.CourseModule;
import com.elearning.model.Instructor;
import java.util.TreeMap;

public class CourseService {
    // Existing Comparator in Java
    // Fast search by key and sorted order
    private TreeMap<String, Course> courses = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private static int nextId;

    public Course addCourse(String title, String description, Instructor instructor, Difficulty difficulty) {
        if (courses.containsKey(title)){
            throw new IllegalArgumentException("A couse with this title already exists: " + title);
        }
        Course course = new Course(++nextId, title, description, instructor, difficulty);
        courses.put(title, course);
        return course;
    }

    public void listAllCourses() {
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }

    public Course findById(int id) {
        for (Course course : courses.values()) {
            if (course.getId() == id) return course;
        }
        return null;
    }

    public CourseModule addModuleToCourse(int courseId, String moduleTitle, int position) {
        Course course = findById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found:" + courseId);
        }
        CourseModule module = new CourseModule(moduleTitle, position);
        course.addModule(module);
        return module;
    }
}
