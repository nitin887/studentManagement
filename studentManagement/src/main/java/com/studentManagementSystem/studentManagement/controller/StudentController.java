package com.studentManagementSystem.studentManagement.controller;

import com.studentManagementSystem.studentManagement.dto.CreateStudentRequestDTO;
import com.studentManagementSystem.studentManagement.dto.StudentDTO;
import com.studentManagementSystem.studentManagement.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing students.
 * Provides endpoints for creating, retrieving, updating, and deleting students.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/students")
@Tag(name = "Students", description = "Operations pertaining to students")
public class StudentController {
    private final StudentService studentService;

    /**
     * Creates a new student and a corresponding user account.
     * The username for the account will be the student's email.
     * @param createStudentRequest The student and initial password data.
     * @return The created student with a 201 Created status.
     */
    @Operation(summary = "Create a new student and their user account (Publicly accessible for registration)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student and user account created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Student or User with this email already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody CreateStudentRequestDTO createStudentRequest) {
        StudentDTO savedStudent = studentService.createStudent(createStudentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    /**
     * Retrieves a list of all students, including their enrollment details.
     * @return A list of all students with a 200 OK status.
     */
    @Operation(summary = "Get a list of all students", description = "Retrieves a list of all students, including their enrollment details.")
    @ApiResponse(responseCode = "200", description = "Found all students")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    /**
     * Retrieves a single student by their ID, including their enrollment details.
     * @param studentId The ID of the student to retrieve.
     * @return The found student with a 200 OK status, or 404 Not Found if it doesn't exist.
     */
    @Operation(summary = "Get a student by their ID", description = "Retrieves a single student by their ID, including their enrollment details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)
    })
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    /**
     * Retrieves a single student by their email address.
     * @param emailId The email address of the student to retrieve.
     * @return The found student with a 200 OK status, or 404 Not Found if it doesn't exist.
     */
    @Operation(summary = "Get a student by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the student",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)
    })
    @GetMapping("/email/{emailId}")
    public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable String emailId) {
        return ResponseEntity.ok(studentService.getStudentByEmail(emailId));
    }

    /**
     * Updates an existing student's personal details.
     * @param studentId The ID of the student to update.
     * @param studentDTO The new data for the student.
     * @return The updated student with a 200 OK status.
     */
    @Operation(summary = "Update an existing student's personal details (Requires ADMIN role)", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StudentDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)
    })
    @PutMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudentById(studentId, studentDTO));
    }

    /**
     * Deletes a student by their ID. This will also cascade and delete their enrollments.
     * @param studentId The ID of the student to delete.
     * @return A 2_04 No Content status on successful deletion.
     */
    @Operation(summary = "Delete a student (Requires ADMIN role)", description = "Deletes a student by their ID. This will also cascade and delete their enrollments.", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found",
                    content = @Content)
    })
    @DeleteMapping("/{studentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudentById(studentId);
        return ResponseEntity.noContent().build();
    }
}