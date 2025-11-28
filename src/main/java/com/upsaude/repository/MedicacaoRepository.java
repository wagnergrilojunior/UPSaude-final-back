package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Medicacao;

/**
 * Repositório para operações de banco de dados relacionadas a Medicações.
 *
 * @author UPSaúde
 */
public interface MedicacaoRepository extends JpaRepository<Medicacao, UUID> {}

