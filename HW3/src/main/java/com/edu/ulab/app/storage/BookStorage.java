package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface BookStorage {
    Map<String, Book> storage = new ConcurrentHashMap<>();

    Book get(String id);

    String create(Book book);

    Boolean update(Book book);

    Boolean delete(String id);

    List<String> getListIdByUserId(String userId);

    Boolean deleteBooksByUserId(String userId);
}
