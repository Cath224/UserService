package com.ateupeonding.userservice.service.impl;

import com.ateupeonding.userservice.service.api.ConfService;
import com.ateupeonding.userservice.service.api.EncryptionService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final ConfService confService;

    public EncryptionServiceImpl(ConfService confService) {
        this.confService = confService;
    }

    @Override
    public String hash(byte[] value, String type, Collection<byte[]> salts) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA3-256");
            messageDigest.update(confService.get(type).getBytes(StandardCharsets.UTF_8));

            if (salts != null && !salts.isEmpty()) {
                for (byte[] salt : salts) {
                    messageDigest.update(salt);
                }
            }
            byte[] result = messageDigest.digest(value);
            return bytesToHex(result);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String encrypt(byte[] value, String type) {
        byte[] typeKeyBytes = confService.get(type).getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[value.length  * 2];
        byte[] sec = new SecureRandom(typeKeyBytes).generateSeed(value.length);
        for (int i = 0; i < value.length; i++) {
            result[i] = (byte) (value[i] - sec[i]);
        }

        if (result.length - value.length >= 0)
            System.arraycopy(sec, 0, result, value.length, result.length - value.length);

        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public byte[] decrypt(String value, String type) {
        byte[] valueBytes = Base64.getDecoder().decode(value);

        byte[] valueKey = Arrays.copyOfRange(valueBytes, 0, valueBytes.length / 2);
        byte[] sec = Arrays.copyOfRange(valueBytes, valueBytes.length / 2, valueBytes.length);

        for (int i = 0; i < valueKey.length; i++) {
            valueKey[i] = (byte) (valueKey[i] + sec[i]);
        }
        return valueKey;
    }


    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
