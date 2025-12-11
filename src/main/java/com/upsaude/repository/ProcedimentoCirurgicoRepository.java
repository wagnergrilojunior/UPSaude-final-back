package com.upsaude.repository;

import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcedimentoCirurgicoRepository extends JpaRepository<ProcedimentoCirurgico, UUID> {

    List<ProcedimentoCirurgico> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId);

    Page<ProcedimentoCirurgico> findByCirurgiaIdOrderByCreatedAtAsc(UUID cirurgiaId, Pageable pageable);

    Page<ProcedimentoCirurgico> findByCodigoProcedimentoOrderByCreatedAtDesc(String codigoProcedimento, Pageable pageable);

    Page<ProcedimentoCirurgico> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);
}
