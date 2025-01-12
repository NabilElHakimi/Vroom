package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Role;
import me.elhakimi.vroom.domain.enums.TypeRole;
import me.elhakimi.vroom.dto.user.request.ChangePassword;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.RestPassword;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
import me.elhakimi.vroom.dto.user.request.mapper.RegisterUserRequestMapper;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.dto.user.response.mapper.RegisterUserResponseMapper;
import me.elhakimi.vroom.repository.UserRepository;
import me.elhakimi.vroom.service.UserService;
import me.elhakimi.vroom.utils.EmailSenderUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final RegisterUserRequestMapper registerUserRequestMapper;
    private final RegisterUserResponseMapper registerUserResponseMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSenderUtil emailSenderUtil;

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUsersByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
//        saveRefreshToken(user);

        return user;
    }

    // Register user ------------------------------------------------------------------------------------------------------------------------------
    public RegisterUserResponseDTO save(RegisterUserRequestDTO user) {

        AppUser existingUser = userRepository.findAppUsersByUsername(user.getUsername());

        if (existingUser != null) {
            throw new IllegalArgumentException("User with username :  " + user.getUsername() + " already exists");
        }

        AppUser checkEmail = userRepository.findAppUsersByEmail(user.getEmail());
        if (checkEmail != null) {
            throw new IllegalArgumentException("User with email :  " + user.getEmail() + " already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        AppUser appUser = registerUserRequestMapper.toAppUser(user);

        Role role = new Role();
        role.setName(TypeRole.USER);
        appUser.setRole(role);

        Random random = new Random();
        int randomInt = random.nextInt(999999);
        String code = String.format("%06d", randomInt);
        appUser.setActivationCode(code);
        appUser.setCreatedAt(Instant.now());
        appUser.setExpiresAt(Instant.now().plusSeconds(60 * 10));
        userRepository.save(appUser);

        sendActivationEmail(appUser);

        return registerUserResponseMapper.toRegisterResponseUserDTO(appUser);
    }

    @Override
    public AppUser saveRefreshToken(AppUser user) {
        System.out.println(user.getRefreshToken());
        return userRepository.save(user);
    }

    @Override
    public AppUser getByRefreshToken(String rToken) {
        return userRepository.findAppUsersByRefreshToken(rToken);
    }

    @Override
    public void logout(String refreshToken) {
        AppUser user = userRepository.findAppUsersByRefreshToken(refreshToken);
        user.setRefreshToken(null);
        userRepository.save(user);
    }


    @Override
    public boolean changePassword(ChangePassword changePassword) {

        AppUser user = userRepository.findAppUsersByUsername(changePassword.username());

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if(!bCryptPasswordEncoder.matches(changePassword.oldPassword() , user.getPassword())){
            throw new IllegalArgumentException("Invalid password");
        }

        if(changePassword.newPassword().equals(changePassword.oldPassword())){
            throw new IllegalArgumentException("New password must be different from the old password");
        }

        if(!changePassword.newPassword().equals(changePassword.confirmPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(bCryptPasswordEncoder.encode(changePassword.newPassword()));

        userRepository.save(user);
        return true;

    }

    @Override
    public boolean forgotPassword(String email) {

        AppUser user = userRepository.findAppUsersByEmail(email);
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }
        Random random = new Random();
        int randomInt = random.nextInt(999999);
        String code = String.format("%06d", randomInt);
        user.setActivationCode(code);
        user.setCreatedAt(Instant.now());
        user.setExpiresAt(Instant.now().plusSeconds(60 * 10));
        user.setUsedCode(false);
        userRepository.save(user);
        sendActivationEmail(user);
        return true;

    }

    @Override
    public boolean resetPassword(RestPassword restPassword) {
        AppUser user = userRepository.findAppUsersByActivationCode(restPassword.code());
        if(user == null){
            throw new IllegalArgumentException("Invalid code");
        }
        if(user.getExpiresAt().isBefore(Instant.now())){
            throw new IllegalArgumentException("Code expired");
        }
        if(!restPassword.confirmPassword().equals(restPassword.password())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        if(user.isUsedCode()){
            throw new IllegalArgumentException("Code already used");
        }

        user.setPassword(bCryptPasswordEncoder.encode(restPassword.password()));
        user.setUsedCode(true);
        userRepository.save(user);
        return true;

    }

    @Override
    public void resendValidation(String username) {
        AppUser user = userRepository.findAppUsersByUsername(username);
        if(user == null){
            throw new IllegalArgumentException("Invalid username");
        }

        if(user.isActif()){
            throw new IllegalArgumentException("User already activated Try with login");
        }

        Random random = new Random();
        int randomInt = random.nextInt(999999);
        String code = String.format("%06d", randomInt);
        user.setActivationCode(code);
        user.setCreatedAt(Instant.now());
        user.setExpiresAt(Instant.now().plusSeconds(60 * 10));
        user.setUsedCode(false);
        userRepository.save(user);
        sendActivationEmail(user);
    }

    public void sendActivationEmail(AppUser user) {
        {
            emailSenderUtil.sendActivationEmail(
                    user.getEmail() ,
                    "Vroom Account Activation" ,
                    "Your activation code is : " + user.getActivationCode()
            );
        }
    }

    public void validateUser(UserValidationRequest validationRequest) {

        AppUser user = userRepository.findAppUsersByActivationCode(validationRequest.getCode());
        if (user == null) {
            throw new IllegalArgumentException("Invalid code");
        }
        if (user.isUsedCode()) {
            throw new IllegalArgumentException("Code already used");
        }
        if (user.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Code expired");
        }

        if(!user.getUsername().equals(validationRequest.getUsername())){
            throw new IllegalArgumentException("Invalid username");
        }

        user.setActif(true);
        user.setUsedCode(true);
        this.userRepository.save(user);

    }

}