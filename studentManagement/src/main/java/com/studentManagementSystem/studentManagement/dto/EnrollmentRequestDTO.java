package com.studentManagementSystem.studentManagement.dto;

import lombok.Data;

@Data
public class EnrollmentRequestDTO {
    private Long studentId;
    private Long courseId;
}