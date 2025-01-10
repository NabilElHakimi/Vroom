package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Validation;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
import me.elhakimi.vroom.dto.user.request.mapper.RegisterUserRequestMapper;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.dto.user.response.mapper.RegisterUserResponseMapper;
import me.elhakimi.vroom.repository.UserRepository;
import me.elhakimi.vroom.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService  {

    private  final UserRepository userRepository;
    private final RegisterUserRequestMapper registerUserRequestMapper;
    private final RegisterUserResponseMapper registerUserResponseMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationServiceImpl validationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public RegisterUserResponseDTO saveUser(RegisterUserRequestDTO user){

        AppUser existingUser = userRepository.findAppUsersByUsername(user.getUsername());

        if(existingUser != null){
            throw new IllegalArgumentException("User with username :  " + user.getUsername() + " already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        AppUser appUser = userRepository.save(registerUserRequestMapper.toAppUser(user));
        this.validationService.save(appUser);
        return registerUserResponseMapper.toRegisterResponseUserDTO(appUser);
    }

    @Override
    public void validateUser(UserValidationRequest validationRequest) {
        Validation validation = validationService.findByCode(validationRequest.getCode());
        if(validation == null){
            throw new IllegalArgumentException("Invalid code");
        }

        if(!validation.getUser().getUsername().equals(validationRequest.getUsername())){
            throw new IllegalArgumentException("Invalid username");
        }

        AppUser user = userRepository.findAppUsersByUsername(validation.getUser().getUsername());
        user.setActif(true);
        this.userRepository.save(user);
        this.validationService.expired(validation);

    }

    @Override
    public void resendValidation(String username) {
        AppUser user = userRepository.findAppUsersByUsername(username);
        if(user == null){
            throw new IllegalArgumentException("Invalid username");
        }
        this.validationService.save(user);
    }
}
