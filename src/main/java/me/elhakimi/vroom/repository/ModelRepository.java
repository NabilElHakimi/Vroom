package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {

}
