package com.edu.ulab.app.dao.impl;

import com.edu.ulab.app.dao.UserDao;
import com.edu.ulab.app.dao.dto.UserDto;
import com.edu.ulab.app.dao.mapper.UserMapperDto;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

    private final UserStorage storage;
    private final UserMapperDto mapper;

    @Autowired
    public UserDaoImpl(UserStorage storage, UserMapperDto mapper) {
        this.storage = storage;
        this.mapper = mapper;
    }

    @Override
    public String create(UserDto userDto) {
        return storage.create(mapper.userDtoToUser(userDto));
    }

    @Override
    public Boolean update(UserDto userDto) {
        return storage.update(mapper.userDtoToUser(userDto));
    }

    @Override
    public UserDto get(String id) {
        return mapper.userToUserDto(storage.get(id));
    }

    @Override
    public Boolean delete(String id) {
        return storage.delete(id);
    }
}