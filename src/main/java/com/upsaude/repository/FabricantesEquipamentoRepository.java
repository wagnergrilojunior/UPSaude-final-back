package com.upsaude.repository;

import com.upsaude.entity.FabricantesEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a FabricantesEquipamento.
 * Esta entidade é de escopo global e não possui relacionamento com Tenant ou Estabelecimento.
 *
 * @author UPSaúde
 */
@Repository
public interface FabricantesEquipamentoRepository extends JpaRepository<FabricantesEquipamento, UUID> {

    /**
     * Busca fabricante por CNPJ.
     */
    Optional<FabricantesEquipamento> findByCnpj(String cnpj);

    /**
     * Busca fabricantes por nome (busca parcial).
     */
    Page<FabricantesEquipamento> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);

    /**
     * Busca todos os fabricantes, ordenados por nome.
     */
    Page<FabricantesEquipamento> findAllByOrderByNomeAsc(Pageable pageable);

    /**
     * Verifica se existe um fabricante de equipamento com o nome informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome do fabricante de equipamento
     * @return true se existe um fabricante de equipamento com este nome, false caso contrário
     */
    boolean existsByNome(String nome);

    /**
     * Verifica se existe um fabricante de equipamento com o nome informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome do fabricante de equipamento
     * @param id ID do fabricante de equipamento a ser excluído da verificação
     * @return true se existe outro fabricante de equipamento com este nome, false caso contrário
     */
    boolean existsByNomeAndIdNot(String nome, UUID id);

    /**
     * Verifica se existe um fabricante de equipamento com o CNPJ informado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param cnpj CNPJ do fabricante de equipamento
     * @return true se existe um fabricante de equipamento com este CNPJ, false caso contrário
     */
    boolean existsByCnpj(String cnpj);

    /**
     * Verifica se existe um fabricante de equipamento com o CNPJ informado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param cnpj CNPJ do fabricante de equipamento
     * @param id ID do fabricante de equipamento a ser excluído da verificação
     * @return true se existe outro fabricante de equipamento com este CNPJ, false caso contrário
     */
    boolean existsByCnpjAndIdNot(String cnpj, UUID id);
}

