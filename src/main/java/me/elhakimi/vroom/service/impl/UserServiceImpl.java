package me.elhakimi.vroom.service.impl;

import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Role;
import me.elhakimi.vroom.domain.enums.TypeRole;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
import me.elhakimi.vroom.dto.user.request.mapper.RegisterUserRequestMapper;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import me.elhakimi.vroom.dto.user.response.mapper.RegisterUserResponseMapper;
import me.elhakimi.vroom.repository.UserRepository;
import me.elhakimi.vroom.service.UserService;
import me.elhakimi.vroom.utils.EmailSenderUtil;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RegisterUserRequestMapper registerUserRequestMapper;
    private final RegisterUserResponseMapper registerUserResponseMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSenderUtil emailSenderUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public RegisterUserResponseDTO saveUser(RegisterUserRequestDTO user) {

        AppUser existingUser = userRepository.findAppUsersByUsername(user.getUsername());

        if (existingUser != null) {
            throw new IllegalArgumentException("User with username :  " + user.getUsername() + " already exists");
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
    public void resendValidation(String username) {
        AppUser user = userRepository.findAppUsersByUsername(username);
        if(user == null){
            throw new IllegalArgumentException("Invalid username");
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