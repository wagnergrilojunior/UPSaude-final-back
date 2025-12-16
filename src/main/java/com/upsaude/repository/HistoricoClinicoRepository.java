package com.upsaude.repository;

import com.upsaude.entity.HistoricoClinico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HistoricoClinicoRepository extends JpaRepository<HistoricoClinico, UUID> {

    @Query("SELECT h FROM HistoricoClinico h WHERE h.id = :id AND h.tenant.id = :tenantId")
    Optional<HistoricoClinico> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT h FROM HistoricoClinico h WHERE h.tenant.id = :tenantId")
    Page<HistoricoClinico> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT h FROM HistoricoClinico h WHERE h.paciente.id = :pacienteId AND h.tenant.id = :tenantId ORDER BY h.dataRegistro DESC")
    Page<HistoricoClinico> findByPacienteIdAndTenantIdOrderByDataRegistroDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT h FROM HistoricoClinico h WHERE h.paciente.id = :pacienteId AND h.tipoRegistro = :tipoRegistro AND h.tenant.id = :tenantId ORDER BY h.dataRegistro DESC")
    Page<HistoricoClinico> findByPacienteIdAndTipoRegistroAndTenantIdOrderByDataRegistroDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("tipoRegistro") String tipoRegistro,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT h FROM HistoricoClinico h WHERE h.paciente.id = :pacienteId AND h.dataRegistro BETWEEN :dataInicio AND :dataFim AND h.tenant.id = :tenantId ORDER BY h.dataRegistro DESC")
    Page<HistoricoClinico> findByPacienteIdAndDataRegistroBetweenAndTenantIdOrderByDataRegistroDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("dataInicio") OffsetDateTime dataInicio,
        @Param("dataFim") OffsetDateTime dataFim,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT h FROM HistoricoClinico h WHERE h.estabelecimento.id = :estabelecimentoId AND h.tenant.id = :tenantId ORDER BY h.dataRegistro DESC")
    Page<HistoricoClinico> findByEstabelecimentoIdAndTenantIdOrderByDataRegistroDesc(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT h FROM HistoricoClinico h WHERE h.profissional.id = :profissionalId AND h.tenant.id = :tenantId ORDER BY h.dataRegistro DESC")
    Page<HistoricoClinico> findByProfissionalIdAndTenantIdOrderByDataRegistroDesc(
        @Param("profissionalId") UUID profissionalId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

}
