        package me.elhakimi.vroom.domain;

        import jakarta.persistence.*;
        import jakarta.validation.constraints.Min;
        import lombok.*;
        import me.elhakimi.vroom.domain.enums.FuelType;
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

            private String mark;
            private String model;

            private String codeCar;

            @Column(columnDefinition = "TEXT")
            private String description ;

            private String telephone ;

            @Min(0)
            private double price ;

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

            private String city;

            @Min(0)
            private Long mileage ;

            @Enumerated(EnumType.STRING)
            private FuelType fuelType;

            private int year;
            private Double pricePerDay;

            private boolean isPublished = false;
            private boolean isArchived = false;


            @OneToMany(mappedBy = "vehicle")
            private List<Reservation> reservations;

            @ManyToOne
            private Location location;

            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;

        }