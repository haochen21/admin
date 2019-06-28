package com.km086.admin.repository.security;

import com.km086.admin.model.security.User;
import com.km086.admin.model.security.UserFilter;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findByFilter(UserFilter paramUserFilter, Integer paramInteger1, Integer paramInteger2);

    Long countByFilter(UserFilter paramUserFilter);

}