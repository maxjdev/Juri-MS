package com.java.ms_cliente.repositories;

import com.java.ms_cliente.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, UUID> {
}
