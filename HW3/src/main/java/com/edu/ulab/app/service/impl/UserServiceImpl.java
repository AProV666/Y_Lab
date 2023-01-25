package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dao.UserDao;
import com.edu.ulab.app.dao.dto.UserDto;
import com.edu.ulab.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao dao;

    @Autowired
    public UserServiceImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public String create(UserDto userDto) {
        return dao.create(userDto);
    }

    @Override
    public Boolean update(UserDto userDto) {
        return dao.update(userDto);
    }

    @Override
    public UserDto get(String id) {
        return dao.get(id);
    }

    @Override
    public Boolean delete(String id) {
        return dao.delete(id);
    }
}