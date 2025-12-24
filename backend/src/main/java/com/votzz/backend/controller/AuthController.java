package com.votzz.backend.controller;

import com.votzz.backend.domain.User;
import com.votzz.backend.repository.UserRepository;
import com.votzz.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite chamadas do Frontend
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private TokenService tokenService;

    // DTO para Login
    record LoginRequest(String email, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) {
        User user = userRepository.findByEmail(data.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Credenciais inválidas"));
        }

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(Map.of(
            "token", token,
            "user", user
        ));
    }

    // NOVO: Método de Registro para resolver o erro 404
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "E-mail já cadastrado"));
        }

        // Criptografa a senha antes de salvar no banco
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Garante um cargo padrão se não for enviado
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("RESIDENT");
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
}