package com.votzz.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestClient restClient;

    public GeminiService() {
        this.restClient = RestClient.create();
    }

    private String callGemini(String prompt) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        // Estrutura do JSON exigida pela API do Gemini
        var requestBody = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", prompt)
                ))
            )
        );

        try {
            // Faz a chamada e mapeia a resposta
            var response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            // Extrai o texto da resposta complexa do JSON do Google
            if (response != null && response.containsKey("candidates")) {
                List<?> candidates = (List<?>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
                    Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
                    List<?> parts = (List<?>) content.get("parts");
                    Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);
                    return (String) firstPart.get("text");
                }
            }
            return "Não foi possível obter resposta da IA.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao comunicar com a IA: " + e.getMessage();
        }
    }

    public String generateAssemblyDescription(String topic, String details) {
        String prompt = String.format("""
            Atue como um gestor condominial profissional. Crie uma descrição formal e detalhada para uma pauta de assembleia com o tema: "%s".
            Detalhes adicionais fornecidos: "%s".
            
            A descrição deve ser clara, imparcial e conter:
            1. Contextualização do problema ou tema.
            2. Justificativa para a votação.
            3. Implicações da decisão.
            
            Mantenha um tom sério e jurídico adequado para condomínios e associações.
            """, topic, details);
        
        return callGemini(prompt);
    }

    public String generateNotification(String title, String endDate) {
        String prompt = String.format("""
            Escreva um rascunho de notificação (email/whatsapp) para convocar moradores para a assembleia "%s". 
            A votação encerra em: %s.
            
            Inclua chamadas para ação (CTA) para votar na plataforma e lembrete sobre a importância do quórum.
            """, title, endDate);

        return callGemini(prompt);
    }

    public String analyzeSentiment(List<String> messages) {
        if (messages == null || messages.isEmpty()) return "Sem dados suficientes para análise.";
        
        String textBlock = String.join("\n", messages);
        String prompt = "Analise as seguintes mensagens do chat da assembleia e forneça um resumo executivo das principais preocupações e o sentimento geral (Positivo, Negativo, Neutro). Não invente opiniões, baseie-se apenas no texto abaixo:\n\n" + textBlock;
        
        return callGemini(prompt);
    }
}