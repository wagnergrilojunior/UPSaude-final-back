package com.upsaude.repository;

import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a ProcedimentoCirurgico.
 *
 * @author UPSaúde
 */
@Repository
public interface ProcedimentoCirurgicoRepository extends JpaRepository<ProcedimentoCirurgico, UUID> {

    /**
     * Busca todos os procedimentos de uma cirurgia, ordenados por criação.
     */
    List<ProcedimentoCirurgico> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId);

    /**
     * Busca todos os procedimentos de uma cirurgia com paginação.
     */
    Page<ProcedimentoCirurgico> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId, Pageable pageable);

    /**
     * Busca procedimentos por código do procedimento.
     */
    Page<ProcedimentoCirurgico> findByCodigoProcedimentoOrderByCreatedAtDesc(String codigoProcedimento, Pageable pageable);

    /**
     * Busca todos os procedimentos de um tenant.
     */
    Page<ProcedimentoCirurgico> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}

