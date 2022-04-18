package com.wiken.example1.service;

import com.wiken.example1.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto) throws Exception;
}
