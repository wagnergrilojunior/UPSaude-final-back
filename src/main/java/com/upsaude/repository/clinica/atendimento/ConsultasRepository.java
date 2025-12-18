package com.upsaude.repository.clinica.atendimento;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.atendimento.Consultas;

public interface ConsultasRepository extends JpaRepository<Consultas, UUID> {

    @Query("SELECT c FROM Consultas c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<Consultas> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM Consultas c WHERE c.tenant.id = :tenantId")
    Page<Consultas> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Consultas> findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataConsultaDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);
}
