package me.elhakimi.vroom.web.controllers.auth;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Qualifier("userServiceImpl")
    private final UserService userService;

    @PostMapping("/register")
    public RegisterUserResponseDTO register(@RequestBody RegisterUserRequestDTO user){

        return userService.saveUser(user);

    }


    @PostMapping("/validate")
    public void validate(@RequestBody UserValidationRequest userValidationRequest){
        userService.validateUser(userValidationRequest);
    }

    @PostMapping("/resend")
    public void resendValidation(@RequestParam String username){
        userService.resendValidation(username);
    }

}
