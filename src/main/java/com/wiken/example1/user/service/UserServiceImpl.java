package com.wiken.example1.user.service;

import com.wiken.example1.user.dto.UserDto;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.exception.UserNotFoundException;
import com.wiken.example1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 유저 생성
     */
    @Override
    public UserDto createUser(UserDto userDto) throws UserNotFoundException {

        UserEntity findUserEntity = userRepository.findByEmail(userDto.getEmail());

        if (findUserEntity != null) {
            throw new UserNotFoundException("중복된 이메일이 존재합니다.");
        }

        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);

        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        UserEntity savedUserEntity = userRepository.save(userEntity);

        UserDto savedUserDto = mapper.map(savedUserEntity, UserDto.class);

        return savedUserDto;
    }

    /**
     * spring security method
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }
}
