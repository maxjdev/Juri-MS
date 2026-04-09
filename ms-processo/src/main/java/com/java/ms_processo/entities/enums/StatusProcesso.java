package com.java.ms_processo.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum StatusProcesso {
	SEM_STATUS("Sem Status", "Processo sem status definido"),
	ABERTO("Aberto", "Processo já foi aberto"),
	EM_ANDAMENTO("Em Andamento", "Processo está em andamento"),
	CONCLUIDO("Concluído", "Processo já está concluído"),
	CANCELADO("Cancelado", "Processo foi cancelado");

	private final String nome;
	private final String descricao;

	public Map<String, String> atributosParaMap() {
		Map<String, String> atributosMap = new HashMap<>();
		atributosMap.put("nome", this.nome);
		atributosMap.put("descricao", this.descricao);
		return atributosMap;
	}

	public static Map<String, Map<String, String>> converteEnumsParaMap() {
		Map<String, Map<String, String>> tiposCalculoMap = new HashMap<>();
		for (StatusProcesso tipo : StatusProcesso.values()) {
			tiposCalculoMap.put(tipo.name(), tipo.atributosParaMap());
		}
		return tiposCalculoMap;
	}

	public static StatusProcesso buscarPorNome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome do StatusProcesso não pode ser vazio");
		}

		for (StatusProcesso tipo : StatusProcesso.values()) {
			if (tipo.nome.equalsIgnoreCase(nome.trim())) {
				return tipo;
			}
		}

		return StatusProcesso.valueOf(nome.toUpperCase());
	}
}