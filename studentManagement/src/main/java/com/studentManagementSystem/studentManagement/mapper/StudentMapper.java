package com.studentManagementSystem.studentManagement.mapper;

import com.studentManagementSystem.studentManagement.dto.CreateStudentRequestDTO;
import com.studentManagementSystem.studentManagement.dto.EnrollmentDTO;
import com.studentManagementSystem.studentManagement.dto.StudentDTO;
import com.studentManagementSystem.studentManagement.entity.Student;

import java.util.stream.Collectors;

public final class StudentMapper {
    private StudentMapper() {}

    public static StudentDTO toStudentDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setEmailId(student.getEmailId());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        studentDTO.setStudentRegistered(student.getStudentRegistered());

        if (student.getEnrollments() != null && !student.getEnrollments().isEmpty()) {
            // This is the key change: we map the enrollments but EXCLUDE the student from the nested DTO.
            studentDTO.setEnrollments(student.getEnrollments().stream()
                    .map(enrollment -> {
                        // Manually create a simplified EnrollmentDTO to break the cycle.
                        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
                        enrollmentDTO.setEnrollmentId(enrollment.getEnrollmentId());
                        enrollmentDTO.setEnrollmentDate(enrollment.getEnrollmentDate());
                        enrollmentDTO.setCourse(CourseMapper.toCourseDTO(enrollment.getCourse()));
                        // DO NOT set the student here, as we are already inside a student.
                        return enrollmentDTO;
                    })
                    .collect(Collectors.toList()));
        }

        return studentDTO;
    }

    /**
     * Maps a CreateStudentRequestDTO to a new Student entity.
     * Note: The password from the DTO is handled by the UserService and is not mapped here.
     * @param requestDTO The DTO containing the data for the new student.
     * @return A new Student entity.
     */
    public static Student toStudentEntity(CreateStudentRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }
        Student student = new Student();
        student.setStudentName(requestDTO.getStudentName());
        student.setEmailId(requestDTO.getEmailId());
        student.setDateOfBirth(requestDTO.getDateOfBirth());
        return student;
    }
}