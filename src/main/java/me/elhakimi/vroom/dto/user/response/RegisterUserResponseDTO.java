package me.elhakimi.vroom.dto.user.response;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserResponseDTO {
    private String first_name;
    private String last_name;
    private String email;
}
