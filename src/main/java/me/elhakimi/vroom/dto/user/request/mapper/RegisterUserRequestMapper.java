package me.elhakimi.vroom.dto.user.request.mapper;

import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.dto.user.request.RegisterUserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserRequestMapper {
     RegisterUserRequestDTO toRegisterUserDTO(AppUser user);
     AppUser toAppUser(RegisterUserRequestDTO registerUserRequestDTO);


}
