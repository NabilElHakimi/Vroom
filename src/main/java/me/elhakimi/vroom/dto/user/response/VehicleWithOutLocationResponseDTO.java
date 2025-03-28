package me.elhakimi.vroom.dto.user.response;

        import me.elhakimi.vroom.domain.Likes;
        import me.elhakimi.vroom.domain.Vehicle;
        import me.elhakimi.vroom.domain.enums.FuelType;
        import me.elhakimi.vroom.domain.enums.VehicleStatus;

        import java.time.LocalDateTime;
        import java.util.List;

        public record VehicleWithOutLocationResponseDTO(
                Long id,
                String codeCar,
                String mark,
                String model,
                String description,
                String telephone,
                double price,
                boolean isPublished,
                boolean isArchived,
                VehicleStatus status,
                List<VehicleImagesResponseDTO> articleImages,
                UserDetailsResponseDTO userDetails,
                List<Likes> likes,
                String city,
                FuelType fuelType,
                int year,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {

            public static VehicleWithOutLocationResponseDTO from(Vehicle vehicle) {
                return new VehicleWithOutLocationResponseDTO(
                        vehicle.getId(),
                        vehicle.getCodeCar(),
                        vehicle.getMark(),
                        vehicle.getModel(),
                        vehicle.getDescription(),
                        vehicle.getTelephone(),
                        vehicle.getPrice(),
                        vehicle.isPublished(),
                        vehicle.isArchived(),
                        vehicle.getStatus(),
                        VehicleImagesResponseDTO.from(vehicle.getVehicleImages()),
                        UserDetailsResponseDTO.from(vehicle.getUser()),
                        vehicle.getLikes(),
                        vehicle.getCity(),
                        vehicle.getFuelType(),
                        vehicle.getYear(),
                        vehicle.getCreatedAt(),
                        vehicle.getUpdatedAt()
                );
            }
        }