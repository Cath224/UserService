package com.ateupeonding.userservice.service.api;

import com.ateupeonding.userservice.model.entity.User;

import java.util.UUID;

public interface UserService {

    User create(User user);

    User update(User user);

    void deleteById(UUID id);

    User getByLogin(String login);

    User getById(UUID id);

}
