package me.elhakimi.vroom.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequestDTO {
    @NonNull
    @NotBlank
    private String first_name;
    @NonNull
    @NotBlank
    private String last_name;

    @NonNull
    @NotBlank
    private String username;
    @NonNull
    @Email
    private String email;
    @NonNull
    private String password;
}
