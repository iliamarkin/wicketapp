package ru.markin.backend.dao.user;

import ru.markin.backend.dao.base.SimpleDao;
import ru.markin.backend.entity.UserEntity;

import java.io.Serializable;
import java.util.List;

public interface UserDao extends SimpleDao<UserEntity, String>, Serializable
{

    List<UserEntity> findAllOrderedBy(UserEntity.Col column);

    List<UserEntity> findByIdsOrderedBy(UserEntity.Col column, String... ids);
}
