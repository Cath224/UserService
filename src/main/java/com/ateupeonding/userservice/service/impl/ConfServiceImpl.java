package com.ateupeonding.userservice.service.impl;

import com.ateupeonding.userservice.model.ConfType;
import com.ateupeonding.userservice.model.error.UserServiceException;
import com.ateupeonding.userservice.service.api.ConfService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfServiceImpl implements ConfService {

    private static final String LOCATION = "configuration/public.conf";

    private final Map<String, String> CONFS = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String get(String type) {
        return CONFS.get(type);
    }

    @PostConstruct
    private void init() {
        ClassPathResource classPathResource = new ClassPathResource(LOCATION);
        try (InputStream is = classPathResource.getInputStream()) {
            byte[] bytes = is.readAllBytes();
            String[] confs = new String(bytes).split("\n");
            CONFS.put(ConfType.ENC, confs[0].replaceAll("\r", ""));
            CONFS.put(ConfType.REF, confs[1].replaceAll("\r", ""));
            CONFS.put(ConfType.VAL, confs[2].replaceAll("\r", ""));
        } catch (IOException e) {
            throw new UserServiceException(e);
        }
    }
}
