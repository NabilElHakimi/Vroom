package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.AppUser;

public record UserDetailsResponseDTO(
        String first_name,
        String username,
        String email,
        String last_name ,
        String image
) {
    public static UserDetailsResponseDTO from(AppUser appUser) {
        return new UserDetailsResponseDTO(
                appUser.getFirst_name(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getLast_name(),
                appUser.getImageUrl()
        );
    }
}
