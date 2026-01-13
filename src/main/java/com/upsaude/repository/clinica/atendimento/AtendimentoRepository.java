package com.upsaude.repository.clinica.atendimento;

import com.upsaude.entity.clinica.atendimento.Atendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

    @Query("SELECT a FROM Atendimento a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<Atendimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM Atendimento a WHERE a.tenant.id = :tenantId")
    Page<Atendimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT a FROM Atendimento a WHERE a.paciente.id = :pacienteId AND a.tenant.id = :tenantId ORDER BY a.informacoes.dataHora DESC")
    Page<Atendimento> findByPacienteIdAndTenantIdOrderByInformacoesDataHoraDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT a FROM Atendimento a WHERE a.profissional.id = :profissionalId AND a.tenant.id = :tenantId ORDER BY a.informacoes.dataHora DESC")
    Page<Atendimento> findByProfissionalIdAndTenantIdOrderByInformacoesDataHoraDesc(
        @Param("profissionalId") UUID profissionalId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT a FROM Atendimento a WHERE a.estabelecimento.id = :estabelecimentoId AND a.tenant.id = :tenantId ORDER BY a.informacoes.dataHora DESC")
    Page<Atendimento> findByEstabelecimentoIdAndTenantIdOrderByInformacoesDataHoraDesc(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT a FROM Atendimento a WHERE a.competenciaFinanceira.id = :competenciaId AND a.tenant.id = :tenantId ORDER BY a.informacoes.dataHora DESC")
    Page<Atendimento> findByCompetenciaFinanceira(
            @Param("competenciaId") UUID competenciaId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT a FROM Atendimento a WHERE a.tenant.id = :tenantId AND a.competenciaFinanceira.id = :competenciaId ORDER BY a.informacoes.dataHora DESC")
    Page<Atendimento> findByTenantAndCompetenciaFinanceira(
            @Param("tenantId") UUID tenantId,
            @Param("competenciaId") UUID competenciaId,
            Pageable pageable);

    @EntityGraph(attributePaths = {"procedimentos", "procedimentos.sigtapProcedimento", "competenciaFinanceira"})
    @Query("SELECT a FROM Atendimento a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<Atendimento> findByIdAndTenantComProcedimentos(@Param("id") UUID id, @Param("tenantId") UUID tenantId);
}
