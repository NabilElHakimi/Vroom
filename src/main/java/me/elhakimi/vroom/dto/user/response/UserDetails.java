package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.AppUser;

public record UserDetails(
        String first_name,
        String username,
        String email,
        String last_name
) {
    public static UserDetails from(AppUser appUser) {
        return new UserDetails(
                appUser.getFirst_name(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getLast_name()

        );
    }
}
