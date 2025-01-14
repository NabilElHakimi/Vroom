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
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)

    private Long id;
    private String title;
    private String description ;
    private String telephone ;
    private double price ;
    private boolean isPublished = false;
    private boolean isArchived = false;
    private int status;

    @OneToMany
    private List<VehicleImages> articleImages;

    @ManyToOne
    private AppUser user ;

    @OneToMany(mappedBy = "vehicle")
    private List<Likes> likes;

    @OneToOne
    private City city;

    @OneToOne(mappedBy = "vehicle")
    private Model model;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
