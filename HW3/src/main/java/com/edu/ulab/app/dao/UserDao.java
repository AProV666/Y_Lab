package com.edu.ulab.app.dao;

import com.edu.ulab.app.dao.dto.UserDto;

public interface UserDao {
    String create(UserDto userDto);

    Boolean update(UserDto userDto);

    UserDto get(String id);

    Boolean delete(String id);
}
