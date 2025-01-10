package me.elhakimi.vroom.web.controllers.auth;


import lombok.AllArgsConstructor;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
