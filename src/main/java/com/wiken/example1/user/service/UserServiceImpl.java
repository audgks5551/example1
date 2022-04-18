package com.wiken.example1.user.service;

import com.wiken.example1.user.dto.UserDto;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.exception.UserNotFoundException;
import com.wiken.example1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) throws UserNotFoundException {
        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        if (userEntity == null) {
            throw new UserNotFoundException("유저를 찾을 수 없습니다.");
        }

        userEntity.setEncryptedPwd("EncryptedPwd");

        UserEntity savedUserEntity = userRepository.save(userEntity);

        UserDto savedUserDto = mapper.map(savedUserEntity, UserDto.class);

        return savedUserDto;
    }
}
