package me.elhakimi.vroom.dto.user.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequestDTO {
    @NonNull
    private String first_name;
    @NonNull
    private String last_name;
    @NonNull
    @Email
    private String email;
    @NonNull
    private String password;
}
