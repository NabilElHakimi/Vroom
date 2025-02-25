package me.elhakimi.vroom.dto.user.request;

import java.time.LocalDateTime;

public record ReservationRequestDTO(
        Long vehicleId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
