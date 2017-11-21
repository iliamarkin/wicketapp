package ru.markin.backend.service;

import ru.markin.backend.dto.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User addUser(User user);
}
