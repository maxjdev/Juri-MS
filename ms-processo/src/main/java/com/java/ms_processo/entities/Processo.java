package com.java.ms_processo.entities;

import com.java.ms_processo.entities.enums.StatusProcesso;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class Processo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    private String numeroProcesso;

    private UUID clienteId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusProcesso status = StatusProcesso.SEM_STATUS;
}
