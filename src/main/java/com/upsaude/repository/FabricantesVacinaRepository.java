package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesVacina;

/**
 * Repositório para a entidade FabricantesVacina.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 */
public interface FabricantesVacinaRepository extends JpaRepository<FabricantesVacina, UUID> {

    /**
     * Verifica se existe um fabricante de vacina com o nome informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome do fabricante de vacina
     * @return true se existe um fabricante de vacina com este nome, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se existe um fabricante de vacina com o nome informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome do fabricante de vacina
     * @param id ID do fabricante de vacina a ser excluído da verificação
     * @return true se existe outro fabricante de vacina com este nome, false caso contrário
     */
    boolean existsByNomeAndIdNot(String nome, UUID id);

    /**
     * Verifica se existe um fabricante de vacina com o CNPJ informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param cnpj CNPJ do fabricante de vacina
     * @return true se existe um fabricante de vacina com este CNPJ, false caso contrário
     */
    boolean existsByCnpj(String cnpj);

    /**
     * Verifica se existe um fabricante de vacina com o CNPJ informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param cnpj CNPJ do fabricante de vacina
     * @param id ID do fabricante de vacina a ser excluído da verificação
     * @return true se existe outro fabricante de vacina com este CNPJ, false caso contrário
     */
    boolean existsByCnpjAndIdNot(String cnpj, UUID id);
}
