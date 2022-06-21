package com.ateupeonding.userservice.service.impl;

import com.ateupeonding.userservice.model.ConfType;
import com.ateupeonding.userservice.model.entity.Seed;
import com.ateupeonding.userservice.model.entity.User;
import com.ateupeonding.userservice.model.error.UserServiceBadRequestException;
import com.ateupeonding.userservice.repository.SeedRepository;
import com.ateupeonding.userservice.service.api.EncryptionService;
import com.ateupeonding.userservice.service.api.SeedService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class SeedServiceImpl implements SeedService {

    private final SeedRepository seedRepository;

    private final EncryptionService encryptionService;

    public SeedServiceImpl(SeedRepository seedRepository, EncryptionService encryptionService) {
        this.seedRepository = seedRepository;
        this.encryptionService = encryptionService;
    }

    @Override
    public Seed create(User user, byte[] valueBytes) {
        String ref = encryptionService.hash(user.getId().toString().getBytes(StandardCharsets.UTF_8),
                ConfType.REF,
                null);
        String value = encryptionService.encrypt(valueBytes, ConfType.VAL);
        Seed seed = new Seed();
        seed.setReferenceId(ref);
        seed.setValue(value);
        seed.setCreatedTimestamp(OffsetDateTime.now());
        seedRepository.save(seed);
        return seed;
    }

    @Override
    public void deleteById(UUID id) {
        seedRepository.deleteById(id);
    }

    @Override
    public void deleteByReferenceId(UUID referenceId) {
        String ref = encryptionService.hash(referenceId.toString().getBytes(StandardCharsets.UTF_8),
                ConfType.REF,
                null);
        seedRepository.deleteByReferenceId(ref);
    }

    @Override
    public Seed getByReferenceId(UUID referenceId) {
        String ref = encryptionService.hash(referenceId.toString().getBytes(StandardCharsets.UTF_8),
                ConfType.REF,
                null);
        Seed seed =  seedRepository.findByReferenceId(ref);
        if (seed == null) {
            throw new UserServiceBadRequestException();
        }
        return seed;
    }
}
