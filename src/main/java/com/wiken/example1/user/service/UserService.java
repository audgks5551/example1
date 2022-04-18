package com.wiken.example1.user.service;

import com.wiken.example1.user.dto.UserDto;
import com.wiken.example1.user.exception.UserNotFoundException;

public interface UserService {
    UserDto createUser(UserDto userDto) throws UserNotFoundException;
}
