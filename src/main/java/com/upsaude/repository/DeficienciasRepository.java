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

    /**
     * Verifica se existe uma deficiência com o nome informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome da deficiência
     * @return true se existe uma deficiência com este nome, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se existe uma deficiência com o nome informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome da deficiência
     * @param id ID da deficiência a ser excluída da verificação
     * @return true se existe outra deficiência com este nome, false caso contrário
     */
    boolean existsByNomeAndIdNot(String nome, UUID id);
}

