package com.finalpbl.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.finalpbl.Dto.User.UserDto;
import com.finalpbl.Model.User;

@Service
public class UserResponseMapper implements Function<User, UserDto>{

    @Override
    public UserDto apply(User user) {
        return new UserDto(
            user.getId(),
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getAddress(),
            user.getPhone(),
            user.getGender(),
            user.getRole().name());
    }
    
}
