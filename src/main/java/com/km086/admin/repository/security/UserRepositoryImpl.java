package com.km086.admin.repository.security;

import com.km086.admin.model.security.User;
import com.km086.admin.model.security.UserFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public List<User> findByFilter(UserFilter filter, Integer startIndex, Integer pageSize) {
        List<User> users = new ArrayList();

        TypedQuery<User> query = filter.createListQuery(this.em);
        if ((startIndex != null) && (pageSize != null)) {
            query.setFirstResult(startIndex.intValue() * pageSize.intValue());
            query.setMaxResults(pageSize.intValue());
        }
        List<User> dbUsers = query.getResultList();
        if ((dbUsers != null) && (dbUsers.size() > 0)) {
            users.addAll(dbUsers);
        }
        return users;
    }

    public Long countByFilter(UserFilter filter) {
        TypedQuery<Long> query = filter.createCountQuery(this.em);
        Long count = (Long) query.getSingleResult();
        return count;
    }
}
