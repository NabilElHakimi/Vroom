package me.elhakimi.vroom.domain;

import jakarta.persistence.*;
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

    private String name;
    private String address;
    private String city;
    private String telephone;
    private String email;

    @OneToMany(mappedBy = "location" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    List<Vehicle> vehicles;

}
