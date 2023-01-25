package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Update user: {}", user);
        Person newPerson = userRepository.save(user);
        log.info("New user: {}", newPerson);
        return userMapper.personToUserDto(newPerson);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user with id: {}", id);
        Person user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
        log.info("User: {}", user);
        return userMapper.personToUserDto(user);
    }

    @Override
    public Boolean deleteUserById(Long id) {
        log.info("Delete user with id: {}", id);
        boolean hasUser = userRepository.findById(id).isEmpty();
        if (hasUser) {
            log.info("User not found: {}", id);
            return false;
        }
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
        return true;
    }
}
