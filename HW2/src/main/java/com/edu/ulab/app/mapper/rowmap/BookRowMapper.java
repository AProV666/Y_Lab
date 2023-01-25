package com.edu.ulab.app.mapper.rowmap;

import com.edu.ulab.app.dto.BookDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<BookDto> {

    @Override
    public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookDto bookDto = new BookDto();
        bookDto.setId(rs.getLong("ID"));
        bookDto.setUserId(rs.getLong("USER_ID"));
        bookDto.setTitle(rs.getString("TITLE"));
        bookDto.setAuthor(rs.getString("AUTHOR"));
        bookDto.setPageCount(rs.getLong("PAGE_COUNT"));
        return bookDto;
    }
}
