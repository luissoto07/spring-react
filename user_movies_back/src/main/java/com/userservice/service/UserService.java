package com.userservice.service;

import com.userservice.domain.Role;
import com.userservice.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUser(String username);
    User saveUser(User user);
    User updateUser(User user, long id);
    void deleteUser(long id);
    Role saveRole(Role role);
    void addRoleToUser(String username,String roleName);

}
