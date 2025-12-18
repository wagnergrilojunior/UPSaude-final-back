package com.upsaude.repository.clinica.medicacao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.medicacao.DispensacoesMedicamentos;

public interface DispensacoesMedicamentosRepository extends JpaRepository<DispensacoesMedicamentos, UUID> {

    @Query("SELECT d FROM DispensacoesMedicamentos d WHERE d.id = :id AND d.tenant.id = :tenantId")
    Optional<DispensacoesMedicamentos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DispensacoesMedicamentos d WHERE d.tenant.id = :tenantId")
    Page<DispensacoesMedicamentos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<DispensacoesMedicamentos> findByEstabelecimentoIdAndTenantIdOrderByDataDispensacaoDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);
}
