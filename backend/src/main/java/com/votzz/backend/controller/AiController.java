package com.votzz.backend.controller;

import com.votzz.backend.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Ajuste conforme sua política de CORS (ex: http://localhost:5173)
public class AiController {

    private final GeminiService geminiService;

    @PostMapping("/description")
    public ResponseEntity<Map<String, String>> generateDescription(@RequestBody Map<String, String> payload) {
        String topic = payload.get("topic");
        String details = payload.get("details");
        String result = geminiService.generateAssemblyDescription(topic, details);
        return ResponseEntity.ok(Map.of("text", result));
    }

    @PostMapping("/notification")
    public ResponseEntity<Map<String, String>> generateNotification(@RequestBody Map<String, String> payload) {
        String title = payload.get("title");
        String endDate = payload.get("endDate");
        String result = geminiService.generateNotification(title, endDate);
        return ResponseEntity.ok(Map.of("text", result));
    }

    @PostMapping("/sentiment")
    public ResponseEntity<Map<String, String>> analyzeSentiment(@RequestBody Map<String, List<String>> payload) {
        List<String> messages = payload.get("messages");
        String result = geminiService.analyzeSentiment(messages);
        return ResponseEntity.ok(Map.of("text", result));
    }
}