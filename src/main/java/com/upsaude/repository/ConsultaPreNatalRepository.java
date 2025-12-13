package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.ConsultaPreNatal;
import com.upsaude.entity.Tenant;

public interface ConsultaPreNatalRepository extends JpaRepository<ConsultaPreNatal, UUID> {

    Page<ConsultaPreNatal> findByEstabelecimentoIdOrderByDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<ConsultaPreNatal> findByTenantOrderByDataConsultaDesc(Tenant tenant, Pageable pageable);

    List<ConsultaPreNatal> findByPreNatalIdOrderByDataConsultaAsc(UUID preNatalId);

    long countByPreNatalId(UUID preNatalId);

    @Query("SELECT c FROM ConsultaPreNatal c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ConsultaPreNatal> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ConsultaPreNatal c WHERE c.tenant.id = :tenantId")
    Page<ConsultaPreNatal> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    List<ConsultaPreNatal> findByPreNatalIdAndTenantIdOrderByDataConsultaAsc(UUID preNatalId, UUID tenantId);

    Page<ConsultaPreNatal> findByEstabelecimentoIdAndTenantIdOrderByDataConsultaDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    long countByPreNatalIdAndTenantId(UUID preNatalId, UUID tenantId);
}
