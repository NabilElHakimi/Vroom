        package me.elhakimi.vroom.domain;

        import jakarta.persistence.*;
        import lombok.*;
        import me.elhakimi.vroom.domain.enums.VehicleStatus;
        import me.elhakimi.vroom.domain.enums.VehicleType;

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

            @Column(columnDefinition = "TEXT")
            private String description ;
            private String telephone ;
            private double price ;
            private boolean isPublished = false;
            private boolean isArchived = false;
            @Enumerated(EnumType.STRING)
            private VehicleType type;

            @Enumerated(EnumType.STRING)
            private VehicleStatus status;

            @OneToMany(mappedBy = "vehicle" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
            private List<VehicleImages> vehicleImages;

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