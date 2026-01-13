package com.upsaude.repository.clinica.atendimento;

import com.upsaude.entity.clinica.atendimento.AtendimentoProcedimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AtendimentoProcedimentoRepository extends JpaRepository<AtendimentoProcedimento, UUID> {

    @Query("SELECT a FROM AtendimentoProcedimento a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<AtendimentoProcedimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM AtendimentoProcedimento a WHERE a.tenant.id = :tenantId")
    Page<AtendimentoProcedimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM AtendimentoProcedimento a WHERE a.atendimento.id = :atendimentoId AND a.tenant.id = :tenantId")
    List<AtendimentoProcedimento> findByAtendimento(@Param("atendimentoId") UUID atendimentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM AtendimentoProcedimento a WHERE a.sigtapProcedimento.id = :sigtapProcedimentoId AND a.tenant.id = :tenantId")
    Page<AtendimentoProcedimento> findBySigtapProcedimento(@Param("sigtapProcedimentoId") UUID sigtapProcedimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM AtendimentoProcedimento a WHERE a.sigtapProcedimento.codigoOficial = :codigoProcedimento AND a.tenant.id = :tenantId")
    Page<AtendimentoProcedimento> findByCodigoProcedimento(@Param("codigoProcedimento") String codigoProcedimento, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM AtendimentoProcedimento a WHERE a.atendimento.id = :atendimentoId AND a.tenant.id = :tenantId ORDER BY a.sigtapProcedimento.codigoOficial ASC")
    List<AtendimentoProcedimento> findByAtendimentoOrderByCodigoProcedimento(@Param("atendimentoId") UUID atendimentoId, @Param("tenantId") UUID tenantId);
}
