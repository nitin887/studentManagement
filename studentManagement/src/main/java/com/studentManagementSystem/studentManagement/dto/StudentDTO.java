package com.studentManagementSystem.studentManagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private Long studentId;
    private String studentName;
    private String emailId;
    private LocalDate dateOfBirth;
    private LocalDateTime studentRegistered;
    private List<EnrollmentDTO> enrollments;

    public StudentDTO() {
    }

    public StudentDTO(Long studentId, String studentName, String emailId, LocalDate dateOfBirth, LocalDateTime studentRegistered, List<EnrollmentDTO> enrollments) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.emailId = emailId;
        this.dateOfBirth = dateOfBirth;
        this.studentRegistered = studentRegistered;
        this.enrollments = enrollments;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getStudentRegistered() {
        return studentRegistered;
    }

    public void setStudentRegistered(LocalDateTime studentRegistered) {
        this.studentRegistered = studentRegistered;
    }

    public List<EnrollmentDTO> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentDTO> enrollments) {
        this.enrollments = enrollments;
    }
}