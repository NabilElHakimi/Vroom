package me.elhakimi.vroom.domain;

import jakarta.persistence.*;
import lombok.*;
import me.elhakimi.vroom.domain.enums.Role;
import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "\"user\"")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email_verification_code;
    private LocalTime verification_code_expiresAt ;
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Article> articles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;

    private LocalTime created_at ;
    private LocalTime updated_at ;

}

