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
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private AppUser user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
