package me.elhakimi.vroom.domain;


import jakarta.persistence.*;
import lombok.*;
import me.elhakimi.vroom.domain.enums.Role;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private boolean enabled;
    private Role role;
    private String email_verification_code;
    private LocalTime verification_code_expiresAt ;
    private LocalTime created_at ;
    private LocalTime updated_at ;

}
