package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MedicacoesContinuas;

/**
 * Repositório para a entidade MedicacoesContinuas.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface MedicacoesContinuasRepository extends JpaRepository<MedicacoesContinuas, UUID> {
}
