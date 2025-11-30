package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Deficiencias;

/**
 * Repositório para operações de banco de dados relacionadas a Deficiências.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 *
 * @author UPSaúde
 */
public interface DeficienciasRepository extends JpaRepository<Deficiencias, UUID> {
}

