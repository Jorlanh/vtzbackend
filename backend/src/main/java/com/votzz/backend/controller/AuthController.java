package com.votzz.backend.controller;

import com.votzz.backend.domain.User;
import com.votzz.backend.repository.UserRepository;
import com.votzz.backend.repository.TenantRepository; // Agora a importação funcionará
import com.votzz.backend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor 
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository; // Injetado via RequiredArgsConstructor
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    record LoginRequest(String email, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) {
        return userRepository.findByEmail(data.email())
            .map(user -> {
                if (passwordEncoder.matches(data.password(), user.getPassword())) {
                    String token = tokenService.generateToken(user);
                    return ResponseEntity.ok(Map.of(
                        "token", token,
                        "user", user
                    ));
                }
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                     .body(Map.of("message", "Credenciais inválidas"));
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                           .body(Map.of("message", "Usuário não encontrado")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "E-mail já cadastrado"));
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("RESIDENT");
            }

            // Busca o primeiro Tenant (Condomínio) cadastrado no DML para associar ao novo morador
            if (user.getTenant() == null) {
                tenantRepository.findAll().stream()
                    .findFirst()
                    .ifPresent(user::setTenant);
            }

            User savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", "Erro ao processar cadastro: " + e.getMessage()));
        }
    }
}