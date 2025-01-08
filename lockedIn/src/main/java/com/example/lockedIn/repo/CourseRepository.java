package com.example.lockedIn.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.lockedIn.model.Course;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findById(Long id);

    List<Course> findAll();

    List<Course> findByName(String name);

    List<Course> findByUsers_Id(Long userId);

    Course findByCode(String code);
}