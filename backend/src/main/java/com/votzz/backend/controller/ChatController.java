package com.votzz.backend.controller;

import com.votzz.backend.domain.ChatMessage;
import com.votzz.backend.repository.ChatMessageRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequiredArgsConstructor // Injeta o repositório automaticamente
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat/{assemblyId}/send")
    @SendTo("/topic/assembly/{assemblyId}")
    public ChatMessageDTO sendMessage(@DestinationVariable UUID assemblyId, ChatMessageDTO messageDTO) {
        
        // 1. Configurar o timestamp
        messageDTO.setTimestamp(LocalDateTime.now());
        messageDTO.setAssemblyId(assemblyId);

        // 2. SALVAR NO BANCO (Persistência do Histórico)
        ChatMessage entity = new ChatMessage();
        entity.setAssemblyId(assemblyId);
        entity.setUserId(messageDTO.getUserId()); // Precisamos do ID do usuário no DTO
        entity.setUserName(messageDTO.getSenderName());
        entity.setContent(messageDTO.getContent());
        entity.setTimestamp(messageDTO.getTimestamp());
        entity.setTenantId(messageDTO.getTenantId()); // Importante para Multi-tenancy

        chatMessageRepository.save(entity);

        return messageDTO;
    }

    @Data // Uso do Lombok para reduzir o código de Getters/Setters
    public static class ChatMessageDTO {
        private String senderName;
        private String content;
        private LocalDateTime timestamp;
        private UUID assemblyId;
        private UUID userId;   // Adicionado para o banco
        private UUID tenantId; // Adicionado para o banco
    }
}