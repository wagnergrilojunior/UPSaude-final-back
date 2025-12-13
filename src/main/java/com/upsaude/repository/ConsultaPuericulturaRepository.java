package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.ConsultaPuericultura;
import com.upsaude.entity.Tenant;

public interface ConsultaPuericulturaRepository extends JpaRepository<ConsultaPuericultura, UUID> {

    Page<ConsultaPuericultura> findByEstabelecimentoIdOrderByDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<ConsultaPuericultura> findByTenantOrderByDataConsultaDesc(Tenant tenant, Pageable pageable);

    List<ConsultaPuericultura> findByPuericulturaIdOrderByDataConsultaAsc(UUID puericulturaId);

    long countByPuericulturaId(UUID puericulturaId);

    @Query("SELECT c FROM ConsultaPuericultura c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ConsultaPuericultura> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ConsultaPuericultura c WHERE c.tenant.id = :tenantId")
    Page<ConsultaPuericultura> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ConsultaPuericultura> findByPuericulturaIdAndTenantIdOrderByDataConsultaAsc(UUID puericulturaId, UUID tenantId, Pageable pageable);

    List<ConsultaPuericultura> findByPuericulturaIdAndTenantIdOrderByDataConsultaAsc(UUID puericulturaId, UUID tenantId);

    Page<ConsultaPuericultura> findByEstabelecimentoIdAndTenantIdOrderByDataConsultaDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);
}
