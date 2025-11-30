package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CidDoencas;

/**
 * Repositório para a entidade CidDoencas.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface CidDoencasRepository extends JpaRepository<CidDoencas, UUID> {
}
