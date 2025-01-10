package me.elhakimi.vroom.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private Article article;

    @ManyToOne
    private AppUser user;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
