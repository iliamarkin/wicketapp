package ru.markin.backend.dao.user;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.markin.backend.entity.UserEntity;
import ru.markin.backend.util.OracleUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository(value = "userDao")
public class UserDaoImpl implements UserDao {
    private static final long serialVersionUID = 2745610187060723430L;

    @Autowired
    private SessionFactory sessionFactory;

    @Nullable
    @Override
    public UserEntity findById(final String s) {
        return (UserEntity) getSession().get(UserEntity.class, s);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserEntity> findByIds(final String... strings) {
        final List<UserEntity> entities = new ArrayList<>();
        entities.addAll(getCriteria()
                .add(OracleUtil.in(UserEntity.Col.ID.getProperty(),
                        Arrays.asList(strings))).list());
        return entities;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserEntity> findAll() {
        return getCriteria().list();
    }

    @Override
    public UserEntity makePersistent(final UserEntity userEntity) {
        getSession().saveOrUpdate(userEntity);
        return userEntity;
    }

    @Override
    public void makeTransient(final UserEntity userEntity) {
        getSession().delete(userEntity);
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserEntity> findAllOrderedBy(final UserEntity.Col column) {
        return getCriteria()
                .addOrder(Order.asc(column.getProperty()))
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserEntity> findByIdsOrderedBy(final UserEntity.Col column, final String... ids) {
        return getCriteria()
                .add(OracleUtil.in(column.getProperty(), Arrays.asList(ids)))
                .addOrder(Order.asc(column.getProperty()))
                .list();
    }

    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private Criteria getCriteria() {
        return getSession().createCriteria(UserEntity.class);
    }
}
