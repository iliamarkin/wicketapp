package ru.markin.backend.dao.base;

import java.util.List;

public interface SimpleDao<Entity, Id>
{

    Entity findById(Id id);

    List<Entity> findByIds(Id... ids);

    List<Entity> findAll();

    Entity makePersistent(Entity entity);

    void makeTransient(Entity entity);

    void flush();
}
