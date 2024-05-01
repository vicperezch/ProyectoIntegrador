package com.bookingsystem.service;

import com.bookingsystem.model.User;
import com.bookingsystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get();

        }

        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updateById(String id, User user) {
        Optional<User> myUser = userRepository.findById(id);

        if (myUser.isPresent()) {
            User updatedUser = myUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setName(user.getName());
            updatedUser.setPassword(user.getPassword());

            userRepository.save(updatedUser);

            return updatedUser;
        }

        return null;
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
