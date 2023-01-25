package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.rowmap.BookRowMapper;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, bookDto.getTitle());
                        ps.setString(2, bookDto.getAuthor());
                        ps.setLong(3, bookDto.getPageCount());
                        ps.setLong(4, bookDto.getUserId());
                        return ps;
                    }
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if (bookDto == null) {
            throw new NotFoundException("Book not found!");
        }
        log.info("Update book with id: {}", bookDto.getId());
        final String UPDATE_SQL = "UPDATE BOOK SET USER_ID = ?, TITLE = ?, AUTHOR = ?, PAGE_COUNT = ? WHERE ID = ?";
        jdbcTemplate.update(UPDATE_SQL, bookDto.getUserId(), bookDto.getTitle(),
                bookDto.getAuthor(), bookDto.getPageCount(), bookDto.getId());
        log.info("Updated book with id: {}", bookDto.getId());
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Get book with id: {}", id);
        final String SELECT_SQL = "SELECT * FROM BOOK WHERE ID = ?";
        BookDto bookDto = jdbcTemplate.queryForObject(SELECT_SQL, new BookRowMapper(), id);
        log.info("Book: {}", bookDto);
        return bookDto;
    }

    @Override
    public Boolean deleteBookById(Long id) {
        log.info("Delete book with id: {}", id);
        final String DELETE_SQL = "DELETE FROM BOOK WHERE ID = ?";
        int success = jdbcTemplate.update(DELETE_SQL, id);
        if (success < 1) {
            log.info("Book not found: {}", id);
            return false;
        }
        log.info("Deleted book with id: {}", id);
        return true;
    }

    @Override
    public List<BookDto> getBookListByUserId(Long id) {
        log.info("Get book by user id: {}", id);
        final String SELECT_USER_ID_SQL = "SELECT * FROM BOOK WHERE USER_ID = ?";
        List<BookDto> bookList = jdbcTemplate.query(SELECT_USER_ID_SQL, new BookRowMapper(), id);
        log.info("Book list: {}", bookList);
        return bookList;
    }
}
