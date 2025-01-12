package me.elhakimi.vroom.service;

import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.dto.user.request.ChangePassword;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import me.elhakimi.vroom.dto.user.request.RestPassword;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;

public interface UserService  {
    RegisterUserResponseDTO save(RegisterUserRequestDTO user);

    void validateUser(UserValidationRequest validationRequest);

    void resendValidation(String username);


    AppUser loadUserByUsername(String userName);
//    void resendValidation(String username);

    AppUser saveRefreshToken(AppUser user);

    AppUser getByRefreshToken(String rToken);

    void logout(String refreshToken);

    boolean changePassword(ChangePassword changePassword);


    boolean forgotPassword(String email);

    boolean resetPassword(RestPassword restPassword);
}
