package com.upsaude.repository;

import com.upsaude.entity.HistoricoHabilitacaoProfissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HistoricoHabilitacaoProfissionalRepository extends JpaRepository<HistoricoHabilitacaoProfissional, UUID> {

    @Query("SELECT h FROM HistoricoHabilitacaoProfissional h WHERE h.id = :id AND h.tenant.id = :tenantId")
    Optional<HistoricoHabilitacaoProfissional> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT h FROM HistoricoHabilitacaoProfissional h WHERE h.tenant.id = :tenantId")
    Page<HistoricoHabilitacaoProfissional> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT h FROM HistoricoHabilitacaoProfissional h WHERE h.profissional.id = :profissionalId AND h.tenant.id = :tenantId ORDER BY h.dataEvento DESC")
    Page<HistoricoHabilitacaoProfissional> findByProfissionalIdAndTenantIdOrderByDataEventoDesc(
        @Param("profissionalId") UUID profissionalId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT h FROM HistoricoHabilitacaoProfissional h WHERE h.profissional.id = :profissionalId AND h.tipoEvento = :tipoEvento AND h.tenant.id = :tenantId ORDER BY h.dataEvento DESC")
    Page<HistoricoHabilitacaoProfissional> findByProfissionalIdAndTipoEventoAndTenantIdOrderByDataEventoDesc(
        @Param("profissionalId") UUID profissionalId,
        @Param("tipoEvento") String tipoEvento,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT h FROM HistoricoHabilitacaoProfissional h WHERE h.profissional.id = :profissionalId AND h.dataEvento BETWEEN :dataInicio AND :dataFim AND h.tenant.id = :tenantId ORDER BY h.dataEvento DESC")
    List<HistoricoHabilitacaoProfissional> findByProfissionalIdAndDataEventoBetweenAndTenantIdOrderByDataEventoDesc(
        @Param("profissionalId") UUID profissionalId,
        @Param("dataInicio") OffsetDateTime dataInicio,
        @Param("dataFim") OffsetDateTime dataFim,
        @Param("tenantId") UUID tenantId);
}
