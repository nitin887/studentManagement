package com.studentManagementSystem.studentManagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for representing a User in API responses.
 * The password is intentionally excluded for security.
 */
@Data
public class UserDTO {
    @Schema(description = "The unique ID of the user.")
    private Long id;

    @Schema(description = "The username for the user account.")
    private String username;

    @Schema(description = "The roles assigned to the user, as a comma-separated string.")
    private String roles;
}