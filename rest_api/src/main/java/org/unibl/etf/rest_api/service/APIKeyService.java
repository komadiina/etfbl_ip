package org.unibl.etf.rest_api.service;

import org.springframework.stereotype.Service;
import org.unibl.etf.rest_api.model.APIKey;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class APIKeyService {
    private static HashMap<String, APIKey> apiKeys = new HashMap<>();

    public APIKey generateKey(String username) {
        if (apiKeys.containsKey(username))
            return apiKeys.get(username);

        String keyValue = java.util.UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusDays(1);
        APIKey key = new APIKey(keyValue, expiry);

        apiKeys.put(username, key);

        return key;
    }

    public boolean isValid(String username, String key) {
        APIKey apiKey = apiKeys.get(username);
        return apiKey != null && apiKey.getKey().equals(key) && apiKey.getValidUntil().isAfter(LocalDateTime.now());
    }

    public void revoke(String username) {
        apiKeys.remove(username);
    }
}
