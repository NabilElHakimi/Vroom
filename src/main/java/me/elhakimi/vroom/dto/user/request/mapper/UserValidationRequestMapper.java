package me.elhakimi.vroom.dto.user.request.mapper;

import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.dto.user.request.UserValidationRequest;

public interface UserValidationRequestMapper {
    UserValidationRequest toUserValidationRequest(AppUser user);
    AppUser toUserApp(UserValidationRequest userValidationRequest);
}
