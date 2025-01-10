package me.elhakimi.vroom.service;

import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {
    RegisterUserResponseDTO saveUser(RegisterUserRequestDTO user);

    void validateUser(UserValidationRequest validationRequest);

    void resendValidation(String username);

//    void resendValidation(String username);

}
