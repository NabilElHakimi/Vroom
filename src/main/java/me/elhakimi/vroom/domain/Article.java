package me.elhakimi.vroom.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    private String title;
    private String description;
    private String telephone;
    private double price;
    private boolean is_published;
    private boolean is_archived;
    private int status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


}
