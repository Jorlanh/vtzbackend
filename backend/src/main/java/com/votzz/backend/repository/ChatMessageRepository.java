package com.votzz.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.votzz.backend.domain.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findByAssemblyIdOrderByTimestampAsc(String assemblyId);
}