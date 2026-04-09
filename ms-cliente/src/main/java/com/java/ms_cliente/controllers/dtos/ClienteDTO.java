package com.java.ms_cliente.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.java.ms_cliente.entities.Cliente;
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
	public static ClienteDTO converteParaDto(Cliente c) {
		if (c == null) return null;
		return new ClienteDTO(c.getId(), c.getNomeCompleto(), c.getCpf(), c.getDataNascimento());
	}

	public Cliente converteParaEntidade() {
		return Cliente.builder()
				 .id(this.id)
				 .nomeCompleto(this.nomeCompleto)
				 .cpf(this.cpf)
				 .dataNascimento(this.dataNascimento)
				 .build();
	}
}
