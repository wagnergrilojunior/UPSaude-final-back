package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Vacinas;

/**
 * Repositório para a entidade Vacinas.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface VacinasRepository extends JpaRepository<Vacinas, UUID> {

    /**
     * Verifica se existe uma vacina com o nome informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome da vacina
     * @return true se existe uma vacina com este nome, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se existe uma vacina com o nome informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome da vacina
     * @param id ID da vacina a ser excluída da verificação
     * @return true se existe outra vacina com este nome, false caso contrário
     */
    boolean existsByNomeAndIdNot(String nome, UUID id);

    /**
     * Verifica se existe uma vacina com o código interno informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param codigoInterno código interno da vacina
     * @return true se existe uma vacina com este código interno, false caso contrário
     */
    boolean existsByCodigoInterno(String codigoInterno);

    /**
     * Verifica se existe uma vacina com o código interno informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param codigoInterno código interno da vacina
     * @param id ID da vacina a ser excluída da verificação
     * @return true se existe outra vacina com este código interno, false caso contrário
     */
    boolean existsByCodigoInternoAndIdNot(String codigoInterno, UUID id);
}
