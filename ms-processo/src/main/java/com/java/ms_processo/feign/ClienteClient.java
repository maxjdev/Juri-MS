package com.java.ms_processo.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.java.ms_processo.controllers.dtos.ClienteDTO;

@FeignClient(name = "feign-ms-cliente", url = "${ms-cliente.url}")
public interface ClienteClient {
	@GetMapping("/clientes/{id}")
	ClienteDTO buscaClientePorId(@PathVariable UUID id);
}
