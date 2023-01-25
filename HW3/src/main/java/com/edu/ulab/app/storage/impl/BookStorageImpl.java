package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.storage.BookStorage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BookStorageImpl implements BookStorage {

    @Override
    public Book get(String id) {
        return storage.get(id);
    }

    @Override
    public String create(Book book) {
        if (book == null) {
            return null;
        }
        final String id = UUID.randomUUID().toString();
        book.setId(id);
        storage.put(id, book);
        return id;
    }

    @Override
    public Boolean update(Book book) {
        if (book == null) {
            return false;
        }
        if (storage.get(book.getId()) == null) {
            return false;
        }
        storage.put(book.getId(), book);
        return true;
    }

    @Override
    public Boolean delete(String id) {
        if (storage.get(id) != null) {
            storage.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getListIdByUserId(String userId) {
        if (userId == null) {
            return null;
        }
        List<String> listId = new ArrayList<>();
        storage.forEach((id, book) -> {
            if (book.getUserId().equals(userId)) {
                listId.add(book.getId());
            }
        });
        return listId.isEmpty() ? null : listId;
    }

    @Override
    public Boolean deleteBooksByUserId(String userId) {
        if (userId == null) {
            return false;
        }
        List<Integer> countList = new ArrayList<>();
        storage.forEach((id, book) -> {
            if (book.getUserId().equals(userId)) {
                storage.remove(id);
                countList.add(1);
            }
        });
        return !countList.isEmpty();
    }

}
