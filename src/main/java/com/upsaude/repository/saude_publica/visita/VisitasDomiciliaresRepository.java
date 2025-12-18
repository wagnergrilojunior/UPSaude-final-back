package com.upsaude.repository.saude_publica.visita;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.saude_publica.visita.VisitasDomiciliares;
import com.upsaude.enums.TipoVisitaDomiciliarEnum;

public interface VisitasDomiciliaresRepository extends JpaRepository<VisitasDomiciliares, UUID> {

    Page<VisitasDomiciliares> findByEstabelecimentoIdOrderByDataVisitaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<VisitasDomiciliares> findByTenantOrderByDataVisitaDesc(Tenant tenant, Pageable pageable);

    Page<VisitasDomiciliares> findByEstabelecimentoIdAndTenantOrderByDataVisitaDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    @Query("SELECT v FROM VisitasDomiciliares v WHERE v.id = :id AND v.tenant.id = :tenantId")
    Optional<VisitasDomiciliares> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT v FROM VisitasDomiciliares v WHERE v.tenant.id = :tenantId ORDER BY v.dataVisita DESC")
    Page<VisitasDomiciliares> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<VisitasDomiciliares> findByEstabelecimentoIdAndTenantIdOrderByDataVisitaDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<VisitasDomiciliares> findByPacienteIdAndTenantIdOrderByDataVisitaDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<VisitasDomiciliares> findByProfissionalIdAndTenantIdOrderByDataVisitaDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<VisitasDomiciliares> findByTipoVisitaAndTenantIdOrderByDataVisitaDesc(TipoVisitaDomiciliarEnum tipoVisita, UUID tenantId, Pageable pageable);

    Page<VisitasDomiciliares> findByDataVisitaBetweenAndTenantIdOrderByDataVisitaDesc(OffsetDateTime inicio, OffsetDateTime fim, UUID tenantId, Pageable pageable);
}
