package com.java.ms_cliente.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feign-ms-processo", url = "${ms-processo.url}")
public interface ProcessoClient {
	@DeleteMapping("/processos/exclui-por-clienteId/{id}")
	void excluiProcessosPorClienteId(@PathVariable UUID id);
}
