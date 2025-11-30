package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EspecialidadesMedicas;

/**
 * Repositório para a entidade EspecialidadesMedicas.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface EspecialidadesMedicasRepository extends JpaRepository<EspecialidadesMedicas, UUID> {
}
