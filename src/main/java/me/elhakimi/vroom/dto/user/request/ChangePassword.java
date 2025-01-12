package me.elhakimi.vroom.dto.user.request;

public record ChangePassword(
        String username,
        String oldPassword,
        String newPassword ,
        String confirmPassword){}
