package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Alergias;

/**
 * Repositório para a entidade Alergias.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface AlergiasRepository extends JpaRepository<Alergias, UUID> {
}
