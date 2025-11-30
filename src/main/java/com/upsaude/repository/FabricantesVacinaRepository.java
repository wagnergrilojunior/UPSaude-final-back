package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesVacina;

/**
 * Repositório para a entidade FabricantesVacina.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface FabricantesVacinaRepository extends JpaRepository<FabricantesVacina, UUID> {
}
