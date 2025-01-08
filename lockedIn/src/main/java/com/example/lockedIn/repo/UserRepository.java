package com.example.lockedIn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lockedIn.model.Course;
import com.example.lockedIn.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAll();

    default void addCourse(Long userId, Course course) {
        User user = findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.getCourses().add(course);
        save(user);
    }

}
