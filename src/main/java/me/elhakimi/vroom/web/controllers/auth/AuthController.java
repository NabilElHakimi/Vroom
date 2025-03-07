package me.elhakimi.vroom.web.controllers.auth;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.dto.user.request.ChangePassword;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.RestPassword;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
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
    public void validate(@RequestBody UserValidationRequest userValidationRequest){
        userService.validateUser(userValidationRequest);
    }

    @PostMapping("/resend")
    public void resendValidation(@RequestParam String username){
        userService.resendValidation(username);
    }

    @PostMapping("/login")
    public Map<String , String> login(@RequestBody RegisterUserRequestDTO user){
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if(authenticate.isAuthenticated()){
                return jwtService.getRefreshTokenAndAccessToken(user.getUsername());
            }

            return Map.of("message", "Invalid credentials");

    }

    @PostMapping("/refresh-token")
    public Map<String, String> refresh(@RequestParam String refreshToken){
        return  jwtService.generateNewToken(refreshToken);

    }

    @PostMapping("/logout")
    public void logout(@RequestParam String refreshToken){
        userService.logout(refreshToken);
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


}
