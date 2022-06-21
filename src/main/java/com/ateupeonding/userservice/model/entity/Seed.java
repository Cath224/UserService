package com.ateupeonding.userservice.model.entity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table(name = "seed", schema = "ateupeonding_user")
@Entity(name = "seed")
public class Seed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String referenceId;

    private String value;

    private OffsetDateTime createdTimestamp;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OffsetDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(OffsetDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}
