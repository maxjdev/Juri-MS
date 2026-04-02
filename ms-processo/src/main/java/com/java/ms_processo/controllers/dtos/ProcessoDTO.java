package com.java.ms_processo.controllers.dtos;

import com.java.ms_processo.entities.Processo;
import com.java.ms_processo.entities.enums.StatusProcesso;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProcessoDTO(
        @Schema(description = "Identificador único do processo", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @NotBlank
        @Schema(description = "Número do processo", example = "0001234-56.2023.8.26.0100")
        String numeroProcesso,

        @NotNull
        @Schema(description = "Identificador do cliente relacionado ao processo", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID clienteId,

        @Schema(description = "Status do processo", example = "EM_ANDAMENTO")
        StatusProcesso status
) {
    public static ProcessoDTO converteParaDto(Processo p) {
        if (p == null) return null;
        return new ProcessoDTO(p.getId(), p.getNumeroProcesso(), p.getClienteId(), p.getStatus());
    }

    public Processo converteParaEntidade() {
        return Processo.builder()
                .id(this.id)
                .numeroProcesso(this.numeroProcesso)
                .clienteId(this.clienteId)
                .status(this.status)
                .build();
    }
}

