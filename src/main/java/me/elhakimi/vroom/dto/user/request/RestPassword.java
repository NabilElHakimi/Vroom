package me.elhakimi.vroom.dto.user.request;

public record RestPassword(
        String code,
        String password,
        String confirmPassword
) {
}
