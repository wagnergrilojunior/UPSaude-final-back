package com.upsaude.repository;

import com.upsaude.entity.FabricantesEquipamento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a FabricantesEquipamento.
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
     * Busca todos os fabricantes de um tenant, ordenados por nome.
     */
    Page<FabricantesEquipamento> findByTenantOrderByNomeAsc(Tenant tenant, Pageable pageable);
}

