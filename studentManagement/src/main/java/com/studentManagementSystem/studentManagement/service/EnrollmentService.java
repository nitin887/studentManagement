package com.studentManagementSystem.studentManagement.service;

import com.studentManagementSystem.studentManagement.dto.EnrollmentDTO;
import com.studentManagementSystem.studentManagement.dto.EnrollmentRequestDTO;
import com.studentManagementSystem.studentManagement.entity.Course;
import com.studentManagementSystem.studentManagement.entity.Enrollment;
import com.studentManagementSystem.studentManagement.entity.Student;
import com.studentManagementSystem.studentManagement.exception.CourseNotFoundException;
import com.studentManagementSystem.studentManagement.exception.EnrollmentAlreadyExistsException;
import com.studentManagementSystem.studentManagement.exception.EnrollmentNotFoundException;
import com.studentManagementSystem.studentManagement.exception.StudentNotFoundException;
import com.studentManagementSystem.studentManagement.mapper.EnrollmentMapper;
import com.studentManagementSystem.studentManagement.repository.CourseRepository;
import com.studentManagementSystem.studentManagement.repository.EnrollmentRepository;
import com.studentManagementSystem.studentManagement.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public EnrollmentDTO enrollStudent(EnrollmentRequestDTO enrollmentRequest) {
        // 1. Find the student
        Student student = studentRepository.findById(enrollmentRequest.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + enrollmentRequest.getStudentId()));

        // 2. Find the course
        Course course = courseRepository.findById(enrollmentRequest.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + enrollmentRequest.getCourseId()));

        // 3. Check if this enrollment already exists
        enrollmentRepository.findByStudentAndCourse(student, course).ifPresent(e -> {
            throw new EnrollmentAlreadyExistsException("Student " + student.getStudentId() + " is already enrolled in course " + course.getCourseId());
        });

        // 4. Create and save the new enrollment
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setStudent(student);
        newEnrollment.setCourse(course);

        Enrollment savedEnrollment = enrollmentRepository.save(newEnrollment);

        return EnrollmentMapper.toEnrollmentDTO(savedEnrollment);
    }

    @Transactional(readOnly = true)
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(EnrollmentMapper::toEnrollmentDTO).toList();
    }

    @Transactional(readOnly = true)
    public EnrollmentDTO getEnrollmentById(Long enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .map(EnrollmentMapper::toEnrollmentDTO)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found with id: " + enrollmentId));
    }

    @Transactional
    public void deleteEnrollmentById(Long enrollmentId) {
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new EnrollmentNotFoundException("Enrollment not found with id: " + enrollmentId);
        }
        enrollmentRepository.deleteById(enrollmentId);
    }
}