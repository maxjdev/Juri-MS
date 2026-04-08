package com.java.ms_processo.controllers;

import com.java.ms_processo.controllers.dtos.ProcessoDTO;
import com.java.ms_processo.entities.Processo;
import com.java.ms_processo.services.ProcessoService;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/processos")
@Tag(name = "Processos", description = "Operações relacionadas a processos")
public class ProcessoController {
    private final ProcessoService service;

    @GetMapping
    @Operation(summary = "Lista os processos", description = "Retorna todos os processos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processos listados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<List<ProcessoDTO>> listaProcessos() {
        List<ProcessoDTO> processos = service.listaProcessos()
                .stream()
                .map(ProcessoDTO::converteParaDto)
                .toList();

        return ResponseEntity.ok(processos);
    }
    
    @PostMapping
    @Operation(summary = "Cria um novo processo", description = "Cria um processo e retorna o recurso criado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Processo criado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<ProcessoDTO> criaProcesso(@Valid @RequestBody ProcessoDTO processoRequest) {
        Processo processoCriado = service.criaProcesso(processoRequest.converteParaEntidade());
        return ResponseEntity.status(HttpStatus.CREATED).body(ProcessoDTO.converteParaDto(processoCriado));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca processo por id", description = "Retorna o processo correspondente ao id informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Processo não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<ProcessoDTO> buscaProcessoPorId(@Parameter(description = "ID do processo", required = true) @PathVariable UUID id) {
        Processo processoEncontrado = service.buscaProcessoPorId(id);
        return ResponseEntity.ok(ProcessoDTO.converteParaDto(processoEncontrado));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um processo", description = "Atualiza os dados do processo identificado pelo id e retorna o recurso atualizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processo atualizado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcessoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Processo não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<ProcessoDTO> atualizaProcesso(@Parameter(description = "ID do processo", required = true) @PathVariable UUID id,
                                                     @Valid @RequestBody ProcessoDTO processoRequest) {
        Processo processoAtualizado = service.atualizaProcesso(id, processoRequest.converteParaEntidade());
        return ResponseEntity.ok(ProcessoDTO.converteParaDto(processoAtualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um processo", description = "Exclui o processo identificado pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Processo excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Processo não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<Void> excluiProcessoPorId(@Parameter(description = "ID do processo", required = true) @PathVariable UUID id) {
        service.excluiProcessoPorId(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("exclui-por-clienteId/{clienteId}")
    @Operation(summary = "Exclui todos os processos de um cliente", description = "Exclui todos os processos de um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Processos excluídos com sucesso", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno", content = @Content)
    })
    public ResponseEntity<Void> excluiProcessoPorClienteId(@Parameter(description = "ID do cliente", required = true) @PathVariable UUID clienteId) {
        service.excluiProcessosPorClienteId(clienteId);
        return ResponseEntity.noContent().build();
    }
}
