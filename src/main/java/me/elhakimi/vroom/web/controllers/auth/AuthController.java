package me.elhakimi.vroom.web.controllers.auth;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.dto.user.request.*;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.security.JwtService;
import me.elhakimi.vroom.service.UserService;
import me.elhakimi.vroom.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Qualifier("userServiceImpl")
    private final UserService userService;
    private final JwtService jwtService;

    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public RegisterUserResponseDTO register(@RequestBody RegisterUserRequestDTO user){
        return userService.save(user);
    }

    @PostMapping("/validate")
    public ResponseEntity<Map<String, String>> validate(@RequestBody UserValidationRequest userValidationRequest) {
        String message = userService.validateUser(userValidationRequest);
        Map<String, String> response = Map.of("message", message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/resend")
    public ResponseEntity<Map<String, Object>> resendValidation(@RequestParam String username) {
        userService.resendValidation(username);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Validation code resent successfully.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequestDTO user, HttpServletResponse response) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.username(), user.password())
            );

            if (authenticate.isAuthenticated()) {
                Map<String, String> tokens = jwtService.getRefreshTokenAndAccessToken(user.username(), response);
                AppUser userDetails = (AppUser) authenticate.getPrincipal();
                return Map.of("token", tokens.get("token") ,
                        "username" , userDetails.getUsername() ,
                        "role" , userDetails.getRole().getName().name()
                );

            } else {
                return Map.of("message", "Invalid credentials");
            }
        } catch (Exception e) {
            return Map.of("message", "Invalid credentials");
        }
    }

    @PostMapping("/refresh-token")
    public Map<String, String> refresh(HttpServletRequest request) {
        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return Map.of("message", "Refresh token not found");
        }

        return jwtService.generateNewToken(refreshToken);
    }


    @PostMapping("/logout")
    public ResponseEntity<Map> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    // Remove the cookie by setting its maxAge to 0
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // Ensure the path matches the original cookie
                    response.addCookie(cookie);
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return new ResponseEntity<>(Map.of("message", "Refresh token not found"), HttpStatus.BAD_REQUEST);
        }

        userService.logout(refreshToken);

        return new ResponseEntity<>(Map.of("message", "Logout successful"), HttpStatus.OK);

    }


    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePassword changePassword) {
        if (userService.changePassword(changePassword)) {
            return ResponseUtil.successResponse("Password changed successfully");
        }
        return ResponseUtil.errorResponse("Bad Request", "Password change failed", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody RestPassword restPassword) {
        userService.resetPassword(restPassword);
        return ResponseUtil.successResponse("Password reset successfully");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestParam String email) {
        if (userService.forgotPassword(email)) {
            return ResponseUtil.successResponse("Password reset code sent to email");
        }
        return ResponseUtil.errorResponse("Bad Request", "Password reset failed", HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/add-image")
    public ResponseEntity<Object> addImage(@RequestParam("imageFile") MultipartFile imageFile,
                                           @RequestParam("username") String username) {
        userService.addImage(imageFile , username);
        return ResponseUtil.successResponse("Image added successfully");
    }

}
