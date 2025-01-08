package com.example.lockedIn.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lockedIn.API.ApiResponse;
import com.example.lockedIn.model.Course;
import com.example.lockedIn.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/assign/{courseId}/{userId}")
    public ResponseEntity<ApiResponse<String>> assignUserToCourse(@PathVariable Long courseId,
            @PathVariable Long userId) {
        boolean success = courseService.assignUserToCourse(courseId, userId);
        if (success) {
            return ResponseEntity.ok(new ApiResponse<>(null, "User assigned to course successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Course or User not found"));
        }
    }

    @PostMapping("/remove/{courseId}/{userId}")
    public void removeUserFromCourse(@PathVariable Long courseId, @PathVariable Long userId) {
        courseService.removeUserFromCourse(courseId, userId);
    }

    @PostMapping("/register")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @GetMapping("/AllCourses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/courses/{userId}")
    public List<Course> getCoursesByUserId(@PathVariable Long userId) {
        return courseService.getCpCoursesByUserID(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        return courseOpt.map(course -> ResponseEntity.ok(new ApiResponse<>(course, "Course found")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(null, "Course with ID " + id + " not found")));
    }

    @GetMapping("/code/{code}")
    public Optional<Course> getCourseByCode(@PathVariable String code) {
        return courseService.getCourseByCode(code);
    }

    @GetMapping("/name/{name}")
    public List<Course> getCoursesByName(@PathVariable String name) {
        return courseService.getCoursesByName(name);
    }

    @GetMapping("/user/{userId}")
    public List<Course> getCoursesForUser(@PathVariable Long userId) {
        return courseService.getCoursesForUser(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            course.setCode(courseDetails.getCode());
            courseService.saveCourse(course);
            return ResponseEntity.ok(new ApiResponse<>(course, "Course updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Course with ID " + id + " not found"));
        }
    }
}
