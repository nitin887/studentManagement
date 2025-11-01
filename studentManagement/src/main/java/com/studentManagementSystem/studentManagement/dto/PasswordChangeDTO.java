package com.studentManagementSystem.studentManagement.dto;

import lombok.Data;

/**
 * DTO for handling password change requests.
 */
@Data
public class PasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
}