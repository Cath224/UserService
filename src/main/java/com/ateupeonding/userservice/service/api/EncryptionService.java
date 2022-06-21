package com.ateupeonding.userservice.service.api;

import java.util.Collection;

public interface EncryptionService {

    String hash(byte[] value, String type, Collection<byte[]> salts);

    String encrypt(byte[] value, String type);

    byte[] decrypt(String value, String type);

}
