package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.rowmap.UserRowMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {
    private final JdbcTemplate jdbcTemplate;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Saved user: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (userDto == null) {
            throw new NotFoundException("User not found!");
        }
        log.info("Update user with id: {}", userDto.getId());
        final String UPDATE_SQL = "UPDATE PERSON SET FULL_NAME = ?, TITLE = ?, AGE = ? WHERE ID = ?";
        jdbcTemplate.update(UPDATE_SQL, userDto.getFullName(), userDto.getTitle(), userDto.getAge(), userDto.getId());
        log.info("Updated user with id: {}", userDto.getId());
        return userDto;
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user with id: {}", id);
        final String SELECT_SQL = "SELECT * FROM PERSON WHERE ID = ?";
        UserDto userDto = null;
        try {
            userDto = jdbcTemplate.queryForObject(SELECT_SQL, new UserRowMapper(), id);
        } catch (DataAccessException e) {
            throw new NotFoundException("User not found!");
        }
        log.info("User: {}", userDto);
        return userDto;
    }

    @Override
    public Boolean deleteUserById(Long id) {
        log.info("Delete user with id: {}", id);
        final String DELETE_SQL = "DELETE FROM PERSON WHERE ID = ?";
        int success = jdbcTemplate.update(DELETE_SQL, id);
        if (success < 1) {
            log.info("User not found: {}", id);
            return false;
        }
        log.info("Deleted user with id: {}", id);
        return true;
    }
}
