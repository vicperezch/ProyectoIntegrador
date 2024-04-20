package com.bookingsystem.service;

import com.bookingsystem.model.User;
import java.util.HashMap;

public interface UserService {
    User save(User user);
    User findById(int id);
    HashMap<Integer, User> findAll();
    User updateById(int id, User user);
    void deleteById(int id);
}
