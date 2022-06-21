package com.ateupeonding.userservice.service.api;

import com.ateupeonding.userservice.model.entity.Seed;
import com.ateupeonding.userservice.model.entity.User;

import java.util.UUID;

public interface SeedService {

    Seed create(User user, byte[] valueBytes);

    void deleteById(UUID id);

    void deleteByReferenceId(UUID referenceId);

    Seed getByReferenceId(UUID referenceId);

}
