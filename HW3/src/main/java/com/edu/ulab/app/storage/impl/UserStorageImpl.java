package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserStorageImpl implements UserStorage {

    @Override
    public User get(String id) {
        return storage.get(id);
    }

    @Override
    public String create(User user) {
        if (user == null) {
            return null;
        }
        final String id = UUID.randomUUID().toString();
        user.setId(id);
        storage.put(id, user);
        return id;
    }

    @Override
    public Boolean update(User user) {
        if (user == null) {
            return false;
        }
        if (storage.get(user.getId()) == null) {
            return false;
        }
        storage.put(user.getId(), user);
        return true;
    }

    @Override
    public Boolean delete(String id) {
        if (storage.get(id) == null) {
            return false;
        }
        storage.remove(id);
        return true;
    }
}
