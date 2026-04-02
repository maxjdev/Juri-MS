package com.java.ms_processo.services;

import com.java.ms_processo.entities.enums.StatusProcesso;
import com.java.ms_processo.feign.ClienteClient;
import com.java.ms_processo.controllers.dtos.ClienteDTO;
import com.java.ms_processo.entities.Processo;
import com.java.ms_processo.exceptions.ProcessoNaoEncontradoException;
import com.java.ms_processo.repositories.IProcessoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessoService {
    private final IProcessoRepository repository;
    private final ClienteClient clienteClient;

    @Transactional
    public Processo criaProcesso(Processo processoRequest) {
        if (processoRequest.getClienteId() != null) {
            try {
                ClienteDTO clienteEncontrado = clienteClient.buscaClientePorId(processoRequest.getClienteId());
                log.info("Cliente validado com sucesso: {}", clienteEncontrado.id());
            } catch (Exception ex) {
                log.error("Erro ao validar cliente: {}", ex.getMessage());
                throw ex;
            }
        }

        if (processoRequest.getStatus() == null)
            processoRequest.setStatus(StatusProcesso.SEM_STATUS);

        Processo processoSalvo = repository.save(processoRequest);

        log.info("Processo criado com sucesso: {}", processoSalvo);

        return processoSalvo;
    }

    @Transactional(readOnly = true)
    public Processo buscaProcessoPorId(UUID id) {
        Optional<Processo> processoOptional = repository.findById(id);

        if (processoOptional.isEmpty()) {
            log.warn("Processo com id {} não encontrado", id);
            throw new ProcessoNaoEncontradoException("Processo não encontrado");
        }

        Processo processoEncontrado = processoOptional.get();
        log.info("Processo encontrado: {}", processoEncontrado);

        return processoEncontrado;
    }

    @Transactional
    public Processo atualizaProcesso(UUID id, Processo processoRequest) {
        Processo processoEncontrado = this.buscaProcessoPorId(id);

        processoEncontrado.setNumeroProcesso(processoRequest.getNumeroProcesso());
        processoEncontrado.setStatus(processoRequest.getStatus());

        if (processoRequest.getClienteId() != null &&
                !processoRequest.getClienteId().equals(processoEncontrado.getClienteId())) {
            try {
                ClienteDTO clienteEncontrado = clienteClient.buscaClientePorId(processoRequest.getClienteId());
                log.info("Cliente validado com sucesso: {}", clienteEncontrado.id());
                processoEncontrado.setClienteId(processoRequest.getClienteId());
            } catch (Exception ex) {
                log.error("Erro ao validar cliente: {}", ex.getMessage());
                throw ex;
            }
        }

        Processo processoAtualizado = repository.save(processoEncontrado);
        log.info("Processo atualizado com sucesso: {}", processoAtualizado);

        return processoAtualizado;
    }

    @Transactional
    public void excluiProcessoPorId(UUID id) {
        Processo processoEncontrado = this.buscaProcessoPorId(id);
        repository.delete(processoEncontrado);
    }

    @Transactional
    public void excluiProcessosPorClienteId(UUID clienteId) {
        try {
            List<Processo> processosEncontrados = repository.findAllByClienteId(clienteId);

            if (!processosEncontrados.isEmpty())
                repository.deleteAll(processosEncontrados);

            log.info("Processos relacionados ao cliente {} excluídos com sucesso", clienteId);
        } catch (Exception ex) {
            log.error("Erro ao excluir processos relacionados ao cliente {}: {}", clienteId, ex.getMessage());
            throw ex;
        }
    }
}