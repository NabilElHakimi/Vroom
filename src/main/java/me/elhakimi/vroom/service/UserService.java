package me.elhakimi.vroom.service;

import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    RegisterUserResponseDTO saveUser(RegisterUserRequestDTO user);
}
