package com.elearning.service;

// Adding new course
// TreeMap, sorts automatically

import com.elearning.model.Course;

import java.util.TreeMap;

public class CourseService {
    // Existing Comparator in Java
    // Fast search by key and sorted order
    private TreeMap<String, Course> courses = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void addCourse(Course course) {
        if (courses.containsKey(course.getTitle())){
            throw new IllegalArgumentException("A couse with this title already exists: " + course.getTitle());
        }
        courses.put(course.getTitle(), course);
    }

    public void listAllCourses() {
        for (Course course : courses.values()) {
            System.out.println(course);
        }
    }

}
