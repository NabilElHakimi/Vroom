package me.elhakimi.vroom.web.controllers.auth;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.security.JwtService;
import me.elhakimi.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestParam String refreshToken){
        return  jwtService.generateNewToken(refreshToken);

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


}
