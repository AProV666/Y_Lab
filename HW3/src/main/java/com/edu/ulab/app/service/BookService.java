package com.edu.ulab.app.service;


import com.edu.ulab.app.dao.dto.BookDto;

import java.util.List;

public interface BookService {
    String create(BookDto bookDto);

    Boolean update(BookDto bookDto);

    BookDto get(String id);

    Boolean delete(String id);

    List<String> getListIdByUserId(String userId);

    Boolean deleteBooksByUserId(String userId);
}