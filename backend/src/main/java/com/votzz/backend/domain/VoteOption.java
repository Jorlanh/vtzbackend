package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String label;
}