package me.elhakimi.vroom.web.controllers.user;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.dto.user.response.ProfileResponseDTO;
import me.elhakimi.vroom.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getUser(@RequestParam String username) {
        return ResponseEntity.ok(userService.getProfile(username));
    }
}
