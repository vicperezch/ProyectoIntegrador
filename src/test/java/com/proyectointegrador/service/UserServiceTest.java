package com.proyectointegrador.service;

import com.proyectointegrador.model.User;
import com.proyectointegrador.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void testSaveNewUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertEquals(user, result);
    }

    @Test
    public void testFindExistingUserById() {
        User user = new User();
        when(userRepository.findById("123")).thenReturn(Optional.of(user));

        User result = userService.findById("123");

        assertEquals(user, result);
    }

    @Test
    public void testFindAll() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(users, result);
    }

    @Test
    public void testUpdateById() {
        User oldUser = new User();
        User newUser = new User("newName", "newEmail", "newPassword", "USER");

        when(userRepository.findById("123")).thenReturn(Optional.of(oldUser));
        when(userRepository.save(oldUser)).thenReturn(newUser);

        User result = userService.updateById("123", newUser);

        assertEquals(oldUser, result);
        assertEquals("newName", oldUser.getName());
        assertEquals("newEmail", oldUser.getEmail());
        assertEquals("newPassword", oldUser.getPassword());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(userRepository).deleteById("123");

        userService.deleteById("123");

        verify(userRepository, times(1)).deleteById("123");
    }
}
