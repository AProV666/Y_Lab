package com.edu.ulab.app.dao.mapper;

import com.edu.ulab.app.dao.dto.UserDto;
import com.edu.ulab.app.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDto {
    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
