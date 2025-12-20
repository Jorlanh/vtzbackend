package com.votzz.backend.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

@Controller // Note que aqui NÃO é @RestController, é apenas @Controller
public class ChatController {

    /*
     * COMO FUNCIONA:
     * 1. O Frontend (React/Angular) envia a mensagem para: /app/chat/{idDaAssembleia}/send
     * 2. Este método recebe a mensagem.
     * 3. Ele adiciona o horário atual (timestamp).
     * 4. O @SendTo encaminha a mensagem automaticamente para quem está ouvindo em: /topic/assembly/{idDaAssembleia}
     */
    @MessageMapping("/chat/{assemblyId}/send")
    @SendTo("/topic/assembly/{assemblyId}")
    public ChatMessageDTO sendMessage(@DestinationVariable String assemblyId, ChatMessageDTO message) {
        message.setTimestamp(LocalDateTime.now());
        
        // DICA: Se você quiser SALVAR o histórico do chat no banco de dados, 
        // é aqui que você chamaria o repository.save(message).
        // Por enquanto, ele apenas repassa a mensagem ao vivo.
        
        return message;
    }

    // DTO (Data Transfer Object) para não expor a entidade do banco diretamente
    // Pode deixar aqui dentro como estático ou criar um arquivo separado na pasta 'dto'
    public static class ChatMessageDTO {
        public String senderName;
        public String content;
        public LocalDateTime timestamp;
        public String assemblyId; // Para saber de qual assembleia é
        
        // Getters e Setters são necessários para o Jackson converter o JSON
        public String getSenderName() { return senderName; }
        public void setSenderName(String senderName) { this.senderName = senderName; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}