package com.upsaude.repository.clinica.atendimento;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.clinica.atendimento.Consulta;

public interface ConsultasRepository extends JpaRepository<Consulta, UUID> {

    @Query("SELECT c FROM Consulta c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<Consulta> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM Consulta c WHERE c.tenant.id = :tenantId")
    Page<Consulta> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM Consulta c JOIN c.atendimento a WHERE a.estabelecimento.id = :estabelecimentoId AND c.tenant.id = :tenantId ORDER BY c.informacoes.dataConsulta DESC")
    Page<Consulta> findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataConsultaDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM Consulta c JOIN c.atendimento a WHERE a.paciente.id = :pacienteId AND c.tenant.id = :tenantId ORDER BY c.informacoes.dataConsulta DESC")
    Page<Consulta> findByPacienteIdAndTenantIdOrderByInformacoesDataConsultaDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);
}
