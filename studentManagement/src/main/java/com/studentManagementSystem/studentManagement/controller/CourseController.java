package com.studentManagementSystem.studentManagement.controller;

import com.studentManagementSystem.studentManagement.dto.CourseDTO;
import com.studentManagementSystem.studentManagement.service.CourseService;
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
 * REST controller for managing courses.
 * Provides endpoints for creating, retrieving, updating, and deleting courses.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Operations pertaining to courses")
public class CourseController {
    private final CourseService courseService;

    /**
     * Creates a new course.
     * @param courseDTO The course data to create.
     * @return The created course with a 201 Created status.
     */
    @Operation(summary = "Create a new course (Requires ADMIN or SELLER role)", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Course created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Course with this name already exists",
                    content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    /**
     * Retrieves a list of all courses.
     * @return A list of all courses with a 200 OK status.
     */
    @Operation(summary = "Get a list of all courses")
    @ApiResponse(responseCode = "200", description = "Found all courses")
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    /**
     * Retrieves a single course by its ID.
     * @param courseId The ID of the course to retrieve.
     * @return The found course with a 200 OK status, or 404 Not Found if it doesn't exist.
     */
    @Operation(summary = "Get a course by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the course",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content)
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    /**
     * Updates an existing course.
     * @param courseId The ID of the course to update.
     * @param courseDTO The new data for the course.
     * @return The updated course with a 200 OK status.
     */
    @Operation(summary = "Update an existing course (Requires ADMIN or SELLER role)", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content)
    })
    @PutMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourseById(courseId, courseDTO));
    }

    /**
     * Deletes a course by its ID.
     * @param courseId The ID of the course to delete.
     * @return A 204 No Content status on successful deletion.
     */
    @Operation(summary = "Delete a course (Requires ADMIN role)", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found",
                    content = @Content)
    })
    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourseById(courseId);
        return ResponseEntity.noContent().build();
    }
}