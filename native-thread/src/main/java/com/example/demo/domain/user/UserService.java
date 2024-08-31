package com.example.demo.domain.user;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();
}