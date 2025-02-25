package me.elhakimi.vroom.dto.user.request;

import java.time.LocalDateTime;

public record ReservationRequestDTO(
        Long userId,
        Long vehicleId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
