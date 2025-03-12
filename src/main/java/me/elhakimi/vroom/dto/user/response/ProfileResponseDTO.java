package me.elhakimi.vroom.dto.user.response;

import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.domain.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public record ProfileResponseDTO(
        UserDetailsResponseDTO user,
        String imageUrl,
        List<ReservationResponseDTO> reservations
) {
    public static ProfileResponseDTO from(AppUser user, List<Reservation> reservations) {
        List<ReservationResponseDTO> reservationResponseDTOs = reservations.stream()
                .map(ReservationResponseDTO::from)
                .collect(Collectors.toList());
        return new ProfileResponseDTO(
                UserDetailsResponseDTO.from(user),
                user.getImageUrl(),
                reservationResponseDTOs
        );
    }
}