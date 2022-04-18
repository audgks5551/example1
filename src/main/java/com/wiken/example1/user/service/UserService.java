package com.wiken.example1.user.service;

import com.wiken.example1.user.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto) throws Exception;
}
