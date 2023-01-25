package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dao.BookDao;
import com.edu.ulab.app.dao.dto.BookDto;
import com.edu.ulab.app.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao dao;

    public BookServiceImpl(BookDao dao) {
        this.dao = dao;
    }

    @Override
    public String create(BookDto bookDto) {
        return dao.create(bookDto);
    }

    @Override
    public Boolean update(BookDto bookDto) {
        return dao.update(bookDto);
    }

    @Override
    public BookDto get(String id) {
        return dao.get(id);
    }

    @Override
    public Boolean delete(String id) {
        return dao.delete(id);
    }

    @Override
    public List<String> getListIdByUserId(String userId) {
        return dao.getListIdByUserId(userId);
    }

    @Override
    public Boolean deleteBooksByUserId(String userId) {
        return dao.deleteBooksByUserId(userId);
    }
}