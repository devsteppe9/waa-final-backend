package edu.miu.waa.controller;

import edu.miu.waa.dto.response.UserOutDto;
import edu.miu.waa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "**")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

  private final UserService userService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserOutDto> getAllUsers() {
    return userService.findAllUsers();
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<String> updateUserStatus(@PathVariable Long id, @RequestParam boolean enabled) {
    boolean result = userService.updateUserStatus(id, enabled);

    if (result) {
      String message = enabled ? "User activated successfully." : "User deactivated successfully.";
      return ResponseEntity.ok(message);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
  }
}
