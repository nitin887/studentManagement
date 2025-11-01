package com.studentManagementSystem.studentManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO {
    private Long enrollmentId;
    private LocalDate enrollmentDate;
    // Includes course details for context
    private CourseDTO course;
    // Includes student details for context
    private StudentDTO student;
}