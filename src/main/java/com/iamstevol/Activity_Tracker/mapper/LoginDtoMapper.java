package com.iamstevol.Activity_Tracker.mapper;

import com.iamstevol.Activity_Tracker.dto.LoginDto;
import com.iamstevol.Activity_Tracker.model.User;

import java.util.function.Function;

public class LoginDtoMapper implements Function<User, LoginDto> {
    @Override
    public LoginDto apply(User user) {
        return new LoginDto(
                user.getEmail(),
                user.getPassword()
        );
    }
}
