package com.studentManagementSystem.studentManagement.controller;

import com.studentManagementSystem.studentManagement.dto.EnrollmentDTO;
import com.studentManagementSystem.studentManagement.dto.EnrollmentRequestDTO;
import com.studentManagementSystem.studentManagement.service.EnrollmentService;
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
 * REST controller for managing student enrollments in courses.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollments", description = "Operations for enrolling students in courses")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    /**
     * Enrolls a student in a course.
     * @param enrollmentRequest A DTO containing the studentId and courseId.
     * @return The created enrollment record with a 201 Created status.
     */
    @Operation(summary = "Enroll a student in a course (Requires STUDENT role)", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student enrolled successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Student or Course not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Student is already enrolled in this course",
                    content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<EnrollmentDTO> enrollStudent(@RequestBody EnrollmentRequestDTO enrollmentRequest) {
        EnrollmentDTO savedEnrollment = enrollmentService.enrollStudent(enrollmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEnrollment);
    }

    /**
     * Retrieves a list of all enrollment records.
     * @return A list of all enrollments with a 200 OK status.
     */
    @Operation(summary = "Get a list of all enrollment records")
    @ApiResponse(responseCode = "200", description = "Found all enrollments")
    @GetMapping
    public ResponseEntity<List<EnrollmentDTO>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    /**
     * Retrieves a single enrollment by its ID.
     * @param enrollmentId The ID of the enrollment to retrieve.
     * @return The found enrollment with a 200 OK status, or 404 Not Found if it doesn't exist.
     */
    @Operation(summary = "Get an enrollment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the enrollment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EnrollmentDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Enrollment not found",
                    content = @Content)
    })
    @GetMapping("/{enrollmentId}")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long enrollmentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(enrollmentId));
    }

    /**
     * Deletes an enrollment by its ID (un-enrolls a student from a course).
     * @param enrollmentId The ID of the enrollment to delete.
     * @return A 204 No Content status on successful deletion.
     */
    @Operation(summary = "Delete an enrollment (un-enrolls a student from a course) (Requires ADMIN role)", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Enrollment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Enrollment not found",
                    content = @Content)
    })
    @DeleteMapping("/{enrollmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollmentId) {
        enrollmentService.deleteEnrollmentById(enrollmentId);
        return ResponseEntity.noContent().build();
    }
}