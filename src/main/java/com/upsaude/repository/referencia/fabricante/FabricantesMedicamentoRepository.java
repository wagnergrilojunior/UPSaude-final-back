package com.upsaude.repository.referencia.fabricante;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.referencia.fabricante.FabricantesMedicamento;

public interface FabricantesMedicamentoRepository extends JpaRepository<FabricantesMedicamento, UUID> {

    boolean existsByNome(String nome);

    boolean existsByNomeAndIdNot(String nome, UUID id);
}

