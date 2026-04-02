package com.java.ms_cliente.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class Cliente implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    private String nomeCompleto;

    @Column(unique = true)
    private String cpf;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
}
