package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Alergias;

/**
 * Repositório para a entidade Alergias.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface AlergiasRepository extends JpaRepository<Alergias, UUID> {

    /**
     * Verifica se existe uma alergia com o nome informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome da alergia
     * @return true se existe uma alergia com este nome, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se existe uma alergia com o nome informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome da alergia
     * @param id ID da alergia a ser excluída da verificação
     * @return true se existe outra alergia com este nome, false caso contrário
     */
    boolean existsByNomeAndIdNot(String nome, UUID id);

    /**
     * Verifica se existe uma alergia com o código interno informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param codigoInterno código interno da alergia
     * @return true se existe uma alergia com este código interno, false caso contrário
     */
    boolean existsByCodigoInterno(String codigoInterno);

    /**
     * Verifica se existe uma alergia com o código interno informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param codigoInterno código interno da alergia
     * @param id ID da alergia a ser excluída da verificação
     * @return true se existe outra alergia com este código interno, false caso contrário
     */
    boolean existsByCodigoInternoAndIdNot(String codigoInterno, UUID id);
}
