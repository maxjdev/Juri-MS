package com.java.ms_processo.repositories;

import com.java.ms_processo.entities.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProcessoRepository extends JpaRepository<Processo, UUID> {
    List<Processo> findAllByClienteId(UUID clienteId);
}
