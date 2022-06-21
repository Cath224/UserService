package com.ateupeonding.userservice.repository;

import com.ateupeonding.userservice.model.entity.Seed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeedRepository extends JpaRepository<Seed, UUID> {

    void deleteByReferenceId(@Param("referenceId") String referenceId);

    Seed findByReferenceId(@Param("referenceId") String referenceId);

}
