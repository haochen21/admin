package com.km086.admin.cache;

import com.km086.admin.model.security.User;

import java.util.ArrayList;
import java.util.List;

public enum UserCache {

    INSTANCE;

    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return this.users;
    }

    public User findByUsername(String username) {
        for (User user : this.users) {
            if (user.getLoginName().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User findById(Long id) {
        for (User user : this.users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }


    public User add(User user) {
        User existingUser = findById(user.getId());
        if (existingUser == null) {
            this.users.add(user);
        }
        return user;
    }
}
