package com.java.ms_cliente.controllers;

import com.java.ms_cliente.controllers.dtos.ClienteDTO;
import com.java.ms_cliente.entities.Cliente;
import com.java.ms_cliente.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClienteController {
    private final ClienteService service;

    @PostMapping
    @Operation(summary = "Cria um novo cliente", description = "Cria um cliente e retorna o recurso criado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<ClienteDTO> criaCliente(@Valid @RequestBody ClienteDTO clienteRequest) {
        Cliente clienteCriado = service.criaCliente(clienteRequest.converteParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteDTO.converteParaDto(clienteCriado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca cliente por id", description = "Retorna o cliente correspondente ao id informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<ClienteDTO> buscaClientePorId(@Parameter(description = "ID do cliente", required = true) @PathVariable UUID id) {
        Cliente clienteEncontrado = service.buscaClientePorId(id);
        return ResponseEntity.ok(ClienteDTO.converteParaDto(clienteEncontrado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um cliente", description = "Atualiza os dados do cliente identificado pelo id e retorna o recurso atualizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<ClienteDTO> atualizaCliente(@Parameter(description = "ID do cliente", required = true) @PathVariable UUID id,
                                                   @Valid @RequestBody ClienteDTO clienteRequest) {
        Cliente clienteAtualizado = service.atualizaCliente(id, clienteRequest.converteParaEntidade());
        return ResponseEntity.ok(ClienteDTO.converteParaDto(clienteAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um cliente", description = "Exclui o cliente identificado pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<Void> excluiClientePorId(@Parameter(description = "ID do cliente", required = true) @PathVariable UUID id) {
        service.excluiClientePorId(id);
        return ResponseEntity.noContent().build();
    }
}
