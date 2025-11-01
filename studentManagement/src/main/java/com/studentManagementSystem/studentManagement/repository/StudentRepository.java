package com.studentManagementSystem.studentManagement.repository;

import com.studentManagementSystem.studentManagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmailId(String emailId);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course")
    List<Student> findAllWithDetails();

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course WHERE s.studentId = :id")
    Optional<Student> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.enrollments e LEFT JOIN FETCH e.course WHERE s.emailId = :email")
    Optional<Student> findByEmailIdWithDetails(@Param("email") String email);
}