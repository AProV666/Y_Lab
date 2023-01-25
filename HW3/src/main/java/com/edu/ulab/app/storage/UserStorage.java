package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface UserStorage {

    Map<String, User> storage = new ConcurrentHashMap<>();

    User get(String id);

    String create(User user);

    Boolean update(User user);

    Boolean delete(String id);
}
