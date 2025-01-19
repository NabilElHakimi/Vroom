package me.elhakimi.vroom.dto.user.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserValidationRequest {
    private String username;
    private String code;
}
