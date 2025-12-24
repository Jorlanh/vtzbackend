package com.votzz.backend.service;

import com.votzz.backend.domain.Vote;
import com.votzz.backend.repository.VoteRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    private final VoteRepository voteRepository;

    public ReportService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public ByteArrayInputStream generateAuditCsv(UUID assemblyId) {
        // Certifique-se que este método findByAssemblyId existe no seu VoteRepository
        List<Vote> votes = voteRepository.findByAssemblyId(assemblyId);

        CSVFormat format = CSVFormat.Builder.create(CSVFormat.DEFAULT)
                .setHeader("NOME", "CPF", "OPÇÃO", "HASH AUDITORIA", "DATA/HORA")
                .build();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(out);
             CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {

            for (Vote vote : votes) {
                // Agora os métodos existem graças à atualização na classe Vote
                csvPrinter.printRecord(
                    vote.getUser() != null ? vote.getUser().getNome() : "Anônimo",
                    vote.getUser() != null ? maskCpf(vote.getUser().getCpf()) : "***",
                    vote.getOpcaoEscolhida(), 
                    vote.getAuditHash(),
                    vote.getTimestamp()
                );
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Falha ao gerar CSV de auditoria: " + e.getMessage());
        }
    }

    private String maskCpf(String cpf) {
        if (cpf == null || cpf.length() < 11) return "***.***.***-**";
        String cleanCpf = cpf.replaceAll("\\D", "");
        if (cleanCpf.length() == 11) {
             return "***." + cleanCpf.substring(3, 6) + "." + cleanCpf.substring(6, 9) + "-**";
        }
        return "***.***.***-**"; 
    }
}