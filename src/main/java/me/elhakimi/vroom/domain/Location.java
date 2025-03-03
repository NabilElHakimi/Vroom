package me.elhakimi.vroom.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "The name must not be blank")
    @NotEmpty(message = "The name must not be empty")
    private String name;

    @NotBlank(message = "The address must not be blank")
    @NotEmpty(message = "The address must not be empty")
    private String address;

    @NotBlank(message = "The city must not be blank")
    @NotEmpty(message = "The city must not be empty")
    private String city;

    @NotBlank(message = "The telephone must not be blank")
    @NotEmpty(message = "The telephone must not be empty")
    private String telephone;

    @NotBlank(message = "The email must not be blank")
    @NotEmpty(message = "The email must not be empty")
    private String email;

    @ManyToOne
    private AppUser user;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Vehicle> vehicles;
}