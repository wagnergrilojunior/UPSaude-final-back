package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesMedicamento;

/**
 * Repositório para a entidade FabricantesMedicamento.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface FabricantesMedicamentoRepository extends JpaRepository<FabricantesMedicamento, UUID> {
}
