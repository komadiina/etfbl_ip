package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.rest_api.model.APIKey;
import org.unibl.etf.rest_api.service.APIKeyService;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private APIKeyService apiKeyService;

    @GetMapping("/public/key")
    public ResponseEntity<?> getApiKey(@RequestParam(name = "username") String username) {
        APIKey key = apiKeyService.generateKey(username);
        return ResponseEntity.ok(key);
    }
}
