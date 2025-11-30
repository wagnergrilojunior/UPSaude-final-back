package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ConselhosProfissionais;

/**
 * Repositório para a entidade ConselhosProfissionais.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface ConselhosProfissionaisRepository extends JpaRepository<ConselhosProfissionais, UUID> {
}
