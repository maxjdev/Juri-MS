package com.java.ms_cliente.services;

import com.java.ms_cliente.entities.Cliente;
import com.java.ms_cliente.exceptions.ClienteNaoEncontradoException;
import com.java.ms_cliente.feign.ProcessoClient;
import com.java.ms_cliente.repositories.IClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClienteService {
    private final IClienteRepository repository;
    private final ProcessoClient processoClient;

    @Transactional
    public Cliente criaCliente(Cliente clienteRequest) {
        Cliente clienteSalvo = repository.save(clienteRequest);
        log.info("Cliente criado com sucesso: {}", clienteSalvo);

        return clienteSalvo;
    }

    @Transactional(readOnly = true)
    public Cliente buscaClientePorId(UUID id) {
        Optional<Cliente> clienteOptional = repository.findById(id);

        if (clienteOptional.isEmpty()) {
            log.warn("Cliente com id {} não encontrado", id);
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }

        Cliente clienteEncontrado = clienteOptional.get();
        log.info("Cliente encontrado: {}", clienteEncontrado);

        return clienteEncontrado;
    }

    @Transactional
    public Cliente atualizaCliente(UUID id, Cliente clienteRequest) {
        Cliente clienteEncontrado = this.buscaClientePorId(id);

        clienteEncontrado.setNomeCompleto(clienteRequest.getNomeCompleto());
        clienteEncontrado.setCpf(clienteRequest.getCpf());
        clienteEncontrado.setDataNascimento(clienteRequest.getDataNascimento());

        Cliente clienteAtualizado = repository.save(clienteEncontrado);
        log.info("Cliente atualizado com sucesso: {}", clienteAtualizado);

        return clienteAtualizado;
    }

    @Transactional
    public void excluiClientePorId(UUID id) {
        Cliente clienteEncontrado = this.buscaClientePorId(id);

        try {
            processoClient.excluiProcessosPorClienteId(id);
            log.info("Processo relacionado ao cliente {} excluído com sucesso", id);
        } catch (Exception ex) {
            log.error("Erro ao excluir processo relacionado ao cliente {}: {}", id, ex.getMessage());
            throw ex;
        }

        repository.delete(clienteEncontrado);
    }
}
