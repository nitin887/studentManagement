package com.studentManagementSystem.studentManagement.controller;

import com.studentManagementSystem.studentManagement.dto.PasswordChangeDTO;
import com.studentManagementSystem.studentManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Tag(name = "Users", description = "Operations pertaining to user accounts")
public class UserController {

    private final UserService userService;

    /**
     * Changes the password for a specific user.
     * A user can only change their own password.
     *
     * @param username The username of the user.
     * @param passwordChangeDTO The DTO containing the old and new passwords.
     * @return A response entity indicating success.
     */
    @Operation(summary = "Change a user's password", security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password updated successfully"),
            @ApiResponse(responseCode = "400", description = "Incorrect old password"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden - you can only change your own password"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{username}/password")
    @PreAuthorize("#username == authentication.principal.username or hasRole('ADMIN')")
    public ResponseEntity<Void> changePassword(@PathVariable String username, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(username, passwordChangeDTO);
        return ResponseEntity.noContent().build();
    }
}