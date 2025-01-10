package me.elhakimi.vroom.repository;

import me.elhakimi.vroom.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

            AppUser findAppUsersByUsername(String username);
            AppUser findAppUsersByActivationCode(String activationCode);

}
