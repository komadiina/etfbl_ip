package org.unibl.etf.rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.rest_api.model.APIKey;
import org.unibl.etf.rest_api.model.db.Client;
import org.unibl.etf.rest_api.model.db.Employee;
import org.unibl.etf.rest_api.model.db.User;
import org.unibl.etf.rest_api.model.dto.LoginDto;
import org.unibl.etf.rest_api.model.dto.LoginResponseDto;
import org.unibl.etf.rest_api.model.dto.RegisterDto;
import org.unibl.etf.rest_api.service.APIKeyService;
import org.unibl.etf.rest_api.service.AuthService;

import java.util.HashMap;

@RestController
@RequestMapping("/api/public/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private APIKeyService apiKeyService;

    @RequestMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        try {
            User user = authService.register(registerDto);
            return ResponseEntity.ok(user.obscure());
        } catch (Exception e) {
            System.out.println(e.getMessage());

            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            // jer klijentska apl ne moze koristiti ovaj servis, podrazumijeva se da samo zaposleni poziva ovaj servis
            Employee user = (Employee)authService.loginEmployee(loginDto.getUsername(), loginDto.getPassword());

            LoginResponseDto responseDto = new LoginResponseDto(apiKeyService.generateKey(loginDto.getUsername()), user);
            responseDto.setUser(user.obscure());
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @RequestMapping("/login/client")
    public ResponseEntity<?> loginClient(@RequestBody LoginDto loginDto) {
        try {
            // jer klijentska apl ne moze koristiti ovaj servis, podrazumijeva se da samo zaposleni poziva ovaj servis
            Client user = authService.loginClient(loginDto.getUsername(), loginDto.getPassword());
            return ResponseEntity.ok(user.obscure());
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@RequestBody RegisterDto registerDto) {
        try {
            registerDto.setUserType("Client");
            User user = authService.registerClient(registerDto);
            return ResponseEntity.ok(user.obscure());
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
