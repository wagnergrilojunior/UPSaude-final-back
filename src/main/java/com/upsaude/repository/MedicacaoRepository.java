package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Medicacao;

/**
 * Repositório para operações de banco de dados relacionadas a Medicações.
 * Medicações são de escopo global, não possuem relação com Tenant ou Estabelecimento.
 *
 * @author UPSaúde
 */
public interface MedicacaoRepository extends JpaRepository<Medicacao, UUID> {
}

