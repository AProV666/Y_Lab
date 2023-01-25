package com.edu.ulab.app.service;

import com.edu.ulab.app.dao.dto.UserDto;

public interface UserService {
    String create(UserDto userDto);

    Boolean update(UserDto userDto);

    UserDto get(String id);

    Boolean delete(String id);
}