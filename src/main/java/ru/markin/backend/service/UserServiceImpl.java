package ru.markin.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.markin.backend.dao.user.UserDao;
import ru.markin.backend.dto.User;
import ru.markin.backend.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsers() {
        return this.userDao.findAllOrderedBy(UserEntity.Col.SURNAME)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public User addUser(final User user) {
        return map(this.userDao.makePersistent(map(user)));
    }

    private User map(final UserEntity entity) {
        final User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setBirthdayDate(entity.getBirthdayDate());
        return user;
    }

    private UserEntity map(final User user) {
        final UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setSurname(user.getSurname());
        entity.setBirthdayDate(user.getBirthdayDate());
        return entity;
    }
}

