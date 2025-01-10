package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.mapper.RegisterUserRequestMapper;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.dto.user.response.mapper.RegisterUserResponseMapper;
import me.elhakimi.vroom.repository.UserRepository;
import me.elhakimi.vroom.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService  {

    private  final UserRepository userRepository;
    private final RegisterUserRequestMapper registerUserRequestMapper;
    private final RegisterUserResponseMapper registerUserResponseMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public RegisterUserResponseDTO saveUser(RegisterUserRequestDTO user){

        AppUser existingUser = userRepository.findUserByEmail(user.getEmail());

        if(existingUser != null){
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        AppUser appUser = userRepository.save(registerUserRequestMapper.toAppUser(user));
        return registerUserResponseMapper.toRegisterResponseUserDTO(appUser);

    }

}
