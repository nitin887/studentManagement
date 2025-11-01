package com.studentManagementSystem.studentManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for creating a new student and their associated user account.
 */
@Data
public class CreateStudentRequestDTO {
    @Schema(description = "The full name of the student.", example = "Jane Doe")
    private String studentName;

    @Schema(description = "The unique email address of the student, which will also be their username.", example = "jane.doe@example.com")
    private String emailId;

    @Schema(description = "The student's date of birth.", example = "2002-08-22")
    private LocalDate dateOfBirth;

    @Schema(description = "The initial password for the student's user account.", example = "strongPassword123")
    private String password;

    public CreateStudentRequestDTO() {
    }

    public CreateStudentRequestDTO(String studentName, String emailId, LocalDate dateOfBirth, String password) {
        this.studentName = studentName;
        this.emailId = emailId;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}