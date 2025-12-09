package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesMedicamento;

/**
 * Repositório para a entidade FabricantesMedicamento.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface FabricantesMedicamentoRepository extends JpaRepository<FabricantesMedicamento, UUID> {

    /**
     * Verifica se existe um fabricante de medicamento com o nome informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome do fabricante de medicamento
     * @return true se existe um fabricante de medicamento com este nome, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se existe um fabricante de medicamento com o nome informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome do fabricante de medicamento
     * @param id ID do fabricante de medicamento a ser excluído da verificação
     * @return true se existe outro fabricante de medicamento com este nome, false caso contrário
     */
    boolean existsByNomeAndIdNot(String nome, UUID id);
}
