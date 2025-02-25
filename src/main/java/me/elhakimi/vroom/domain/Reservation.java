package me.elhakimi.vroom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    private Long id;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Vehicle vehicle;

    private String startDate;
    private String endDate;
    private String status;
    private String paymentMethod;
    private Double totalPrice;

}
