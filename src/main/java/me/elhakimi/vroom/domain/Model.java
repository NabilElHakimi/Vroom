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
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String name;

    @OneToOne
    private Vehicle vehicle;

    @ManyToOne
    private Mark mark;

    private int horsepower;
    private double acceleration;
    private int year;

    private LocalDateTime updated_at;
    private LocalDateTime created_at;



}
