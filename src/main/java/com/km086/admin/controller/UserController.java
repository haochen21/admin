package com.km086.admin.controller;

import com.km086.admin.model.security.Profile;
import com.km086.admin.model.security.User;
import com.km086.admin.model.security.UserFilter;
import com.km086.admin.security.AppUser;
import com.km086.admin.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api"})
public class UserController {
    @Autowired
    private SecurityService securityService;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = {"/agent/{id}"}, method = RequestMethod.GET, produces = {"application/json"})
    public User findUserById(@PathVariable Long id) {
        User user = this.securityService.findUserById(id);
        return user;
    }

    @RequestMapping(value = {"/agent"}, method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public void createUser(@RequestBody User user) {
        log.info("register user: " + user.toString());
        this.securityService.saveUser(user);
    }

    @RequestMapping(value = {"/agent"}, method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
    public void modifyUser(@RequestBody User user) {
        log.info("modify user: " + user.toString());
        this.securityService.updateUser(user);
    }

    @RequestMapping(value = {"/agent/{id}"}, method = RequestMethod.DELETE)
    public void removeUser(@PathVariable Long id) {
        this.securityService.deleteUser(id);
    }

    @RequestMapping(value = {"/user/loginNameExists/{loginName}"}, method = RequestMethod.GET)
    @ResponseBody
    public Boolean existsByLoginName(@PathVariable String loginName) {
        return this.securityService.existsUserByLoginName(loginName);
    }

    @RequestMapping(value = {"/agent/page"}, method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Page<User> pageUserByFilter(@RequestBody UserFilter filter) {
        Page<User> page = this.securityService.pageUserByFilter(filter, PageRequest.of(filter.getPage()-1, filter.getSize()));
        return page;
    }

    @RequestMapping(value = {"/user/agent"}, method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public List<User> findAgent(@RequestBody UserFilter filter) {
        List<User> users = this.securityService.findUserByFilter(filter, null);
        return users;
    }

    @RequestMapping(value = {"/user/password/{password}"}, method = RequestMethod.PUT)
    public void modifyPassword(@PathVariable String password) {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.securityService.modifyUserPassword(appUser.getId(), password);
    }

    @RequestMapping(value = {"/agent/name/{name}"}, method = RequestMethod.GET, produces = {"application/json"})
    public List<User> findAgentByName(@PathVariable String name) {
        UserFilter filter = new UserFilter();
        filter.setName(name);

        AppUser securityUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!securityUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            filter.setId(securityUser.getId());
        }
        filter.setProfile(Profile.AGENT);
        List<User> users = this.securityService.findUserByFilter(filter, null);
        return users;
    }
}

