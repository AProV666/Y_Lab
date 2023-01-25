package com.edu.ulab.app.dao.impl;

import com.edu.ulab.app.dao.BookDao;
import com.edu.ulab.app.dao.dto.BookDto;
import com.edu.ulab.app.dao.mapper.BookMapperDto;
import com.edu.ulab.app.storage.BookStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final BookStorage storage;
    private final BookMapperDto mapper;

    @Autowired
    public BookDaoImpl(BookStorage storage, BookMapperDto mapper) {
        this.storage = storage;
        this.mapper = mapper;
    }

    @Override
    public String create(BookDto bookDto) {
        return storage.create(mapper.bookDtoToBook(bookDto));
    }

    @Override
    public Boolean update(BookDto bookDto) {
        return storage.update(mapper.bookDtoToBook(bookDto));
    }

    @Override
    public BookDto get(String id) {
        return mapper.bookToBookDto(storage.get(id));
    }

    @Override
    public Boolean delete(String id) {
        return storage.delete(id);
    }

    @Override
    public List<String> getListIdByUserId(String userId) {
        return storage.getListIdByUserId(userId);
    }

    @Override
    public Boolean deleteBooksByUserId(String userId) {
        return storage.deleteBooksByUserId(userId);
    }

}
