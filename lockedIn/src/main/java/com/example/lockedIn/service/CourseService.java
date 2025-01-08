package com.example.lockedIn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lockedIn.model.Course;
import com.example.lockedIn.model.User;
import com.example.lockedIn.repo.CourseRepository;
import com.example.lockedIn.repo.UserRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean assignUserToCourse(Long courseId, Long userId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (courseOpt.isPresent() && userOpt.isPresent()) {
            Course course = courseOpt.get();
            User user = userOpt.get();
            course.getUsers().add(user);
            courseRepository.save(course);
            userRepository.addCourse(userId, course);
            return true;
        } else {
            return false;
        }
    }

    public void removeUserFromCourse(Long courseId, Long userId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        Optional<User> userOpt = userRepository.findById(userId);

        if (courseOpt.isPresent() && userOpt.isPresent()) {
            Course course = courseOpt.get();
            User user = userOpt.get();
            course.getUsers().remove(user);
            courseRepository.save(course);
        } else {
            // Handle the case where the course or user is not found
            throw new RuntimeException("Course or User not found");
        }
    }

    public List<Course> getCpCoursesByUserID(Long userId) {
        return courseRepository.findByUsers_Id(userId);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> getCoursesByName(String name) {
        return courseRepository.findByName(name);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> getCoursesForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCourses();
    }

    public Optional<Course> getCourseByCode(String code) {
        return Optional.ofNullable(courseRepository.findByCode(code));
    }
}