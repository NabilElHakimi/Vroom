package me.elhakimi.vroom.dto.user.response.mapper;

import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.dto.user.response.RegisterUserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserResponseMapper {
     RegisterUserResponseDTO toRegisterResponseUserDTO(AppUser user);
     AppUser toAppUser(RegisterUserResponseDTO registerUserDTO);
}
