package com.ateupeonding.userservice.service.impl;

import com.ateupeonding.userservice.model.ConfType;
import com.ateupeonding.userservice.model.entity.User;
import com.ateupeonding.userservice.model.error.ResourceNotFoundException;
import com.ateupeonding.userservice.repository.UserRepository;
import com.ateupeonding.userservice.service.api.EncryptionService;
import com.ateupeonding.userservice.service.api.SeedService;
import com.ateupeonding.userservice.service.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

@Transactional(isolation = Isolation.SERIALIZABLE)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EncryptionService encryptionService;
    private final SeedService seedService;

    public UserServiceImpl(UserRepository userRepository, EncryptionService encryptionService, SeedService seedService) {
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.seedService = seedService;
    }

    @Override
    public User create(User user) {
        String password = user.getPassword();
        byte[] valueBytes = generateValue();
        password = encryptionService.hash(password.getBytes(StandardCharsets.UTF_8), ConfType.ENC, Collections.singleton(valueBytes));
        user.setPassword(password);
        user.setCreatedTimestamp(OffsetDateTime.now());
        user = userRepository.save(user);
        seedService.create(user, valueBytes);
        return user;
    }

    @Override
    public User update(User user) {
        user = getById(user.getId());
        String password = user.getPassword();
        byte[] valueBytes = generateValue();
        password = encryptionService.hash(password.getBytes(StandardCharsets.UTF_8), ConfType.ENC, Collections.singleton(valueBytes));
        user.setPassword(password);
        user.setCreatedTimestamp(OffsetDateTime.now());
        user = userRepository.save(user);
        seedService.deleteByReferenceId(user.getId());
        seedService.create(user, valueBytes);
        return user;
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
        seedService.deleteByReferenceId(id);
    }

    @Override
    public User getByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new ResourceNotFoundException("user", "login", login);
        }
        return user;
    }

    @Override
    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
    }


    private static byte[] generateValue() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] value = new byte[245];
        secureRandom.nextBytes(value);
        for (int i = 0; i < value.length; i++) {
            value[i] >>= 32;
        }
        return value;
    }
}
