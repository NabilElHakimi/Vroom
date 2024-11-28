package me.elhakimi.vroom.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String name;

    private LocalDateTime updated_at;
    private LocalDateTime created_at;


}
