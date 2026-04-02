package com.java.ms_processo.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteDTO(
        @Schema(description = "Identificador único do cliente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID id,

        @NotBlank
        @Schema(description = "Nome completo do cliente", example = "Fulano da Silva")
        String nomeCompleto,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
        @Schema(description = "CPF do cliente (somente números)", example = "12345678900")
        String cpf,

        @Past
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @Schema(description = "Data de nascimento no formato dd/MM/yyyy", example = "01/01/1990")
        LocalDate dataNascimento
) {
}

