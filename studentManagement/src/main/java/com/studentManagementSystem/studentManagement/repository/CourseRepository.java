package com.studentManagementSystem.studentManagement.repository;

import com.studentManagementSystem.studentManagement.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseName(String courseName);
}