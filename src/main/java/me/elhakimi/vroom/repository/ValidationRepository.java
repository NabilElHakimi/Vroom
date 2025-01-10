package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationRepository  extends JpaRepository<Validation , Long> {

    Validation findByCodeAndActivationAtNull(String code);
}
