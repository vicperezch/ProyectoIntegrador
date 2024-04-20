package com.bookingsystem.service;

import com.bookingsystem.model.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class UserServiceImp implements UserService {
    private final HashMap<Integer, User> usersMap;
    private int idCount;

    public UserServiceImp() {
        this.usersMap = new HashMap<>();
        this.idCount = 0;
    }

    @Override
    public User save(User user) {
        this.idCount++;

        user.setId(this.idCount);
        usersMap.put(user.getId(), user);

        return user;
    }

    @Override
    public User findById(int id) {
        return usersMap.get(id);
    }

    @Override
    public HashMap<Integer, User> findAll() {
        return usersMap;
    }

    @Override
    public User updateById(int id, User user) {
        User updatedUser = usersMap.get(id);

        if (updatedUser != null) {
            updatedUser.setEmail(user.getEmail());
            updatedUser.setName(user.getName());
            updatedUser.setPassword(user.getPassword());

            usersMap.replace(id, updatedUser);

            return updatedUser;
        }

        return null;
    }

    @Override
    public void deleteById(int id) {
        usersMap.remove(id);
    }
}
