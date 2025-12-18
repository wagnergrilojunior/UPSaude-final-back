package com.upsaude.repository.clinica.atendimento;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.atendimento.ConsultaPuericultura;

public interface ConsultaPuericulturaRepository extends JpaRepository<ConsultaPuericultura, UUID> {
    long countByPuericulturaIdAndTenantId(UUID puericulturaId, UUID tenantId);

    @Query("SELECT c FROM ConsultaPuericultura c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ConsultaPuericultura> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ConsultaPuericultura c WHERE c.tenant.id = :tenantId")
    Page<ConsultaPuericultura> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ConsultaPuericultura> findByPuericulturaIdAndTenantIdOrderByDataConsultaAsc(UUID puericulturaId, UUID tenantId, Pageable pageable);

    Page<ConsultaPuericultura> findByEstabelecimentoIdAndTenantIdOrderByDataConsultaDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);
}
