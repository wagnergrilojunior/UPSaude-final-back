package com.upsaude.repository;

import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Equipamentos.
 *
 * @author UPSaúde
 */
@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamentos, UUID> {

    /**
     * Busca equipamento por código CNES.
     */
    Optional<Equipamentos> findByCodigoCnes(String codigoCnes);

    /**
     * Busca equipamento por registro ANVISA.
     */
    Optional<Equipamentos> findByRegistroAnvisa(String registroAnvisa);

    /**
     * Busca equipamentos por nome (busca parcial).
     */
    Page<Equipamentos> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable pageable);

    /**
     * Busca equipamentos por tipo, ordenados por nome.
     */
    Page<Equipamentos> findByTipoOrderByNomeAsc(TipoEquipamentoEnum tipo, Pageable pageable);

    /**
     * Busca equipamentos por fabricante, ordenados por nome.
     */
    Page<Equipamentos> findByFabricanteIdOrderByNomeAsc(UUID fabricanteId, Pageable pageable);

    /**
     * Busca equipamentos por status, ordenados por nome.
     */
    Page<Equipamentos> findByStatusOrderByNomeAsc(StatusAtivoEnum status, Pageable pageable);

    /**
     * Busca equipamentos ativos, ordenados por nome.
     */
    Page<Equipamentos> findByStatusAndActiveTrueOrderByNomeAsc(StatusAtivoEnum status, Pageable pageable);

    /**
     * Busca todos os equipamentos de um tenant, ordenados por nome.
     */
    Page<Equipamentos> findByTenantOrderByNomeAsc(Tenant tenant, Pageable pageable);
}

