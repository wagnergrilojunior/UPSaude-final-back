package com.upsaude.repository.vacinacao;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.vacinacao.AplicacaoVacina;
import com.upsaude.entity.vacinacao.StatusAplicacao;

@Repository
public interface AplicacaoVacinaRepository extends JpaRepository<AplicacaoVacina, UUID> {

        @Query("SELECT a FROM AplicacaoVacina a WHERE a.paciente.id = :pacienteId " +
                        "AND a.tenant.id = :tenantId ORDER BY a.dataAplicacao DESC")
        List<AplicacaoVacina> findByPacienteIdAndTenantIdOrderByDataAplicacaoDesc(
                        @Param("pacienteId") UUID pacienteId,
                        @Param("tenantId") UUID tenantId);

        @Query("SELECT a FROM AplicacaoVacina a WHERE a.paciente.id = :pacienteId " +
                        "AND a.imunobiologico.id = :imunobiologicoId AND a.tenant.id = :tenantId")
        List<AplicacaoVacina> findByPacienteIdAndImunobiologicoIdAndTenantId(
                        @Param("pacienteId") UUID pacienteId,
                        @Param("imunobiologicoId") UUID imunobiologicoId,
                        @Param("tenantId") UUID tenantId);

        @Query("SELECT a FROM AplicacaoVacina a WHERE a.tenant.id = :tenantId " +
                        "AND a.dataAplicacao BETWEEN :inicio AND :fim ORDER BY a.dataAplicacao DESC")
        List<AplicacaoVacina> findByTenantIdAndDataAplicacaoBetweenOrderByDataAplicacaoDesc(
                        @Param("tenantId") UUID tenantId,
                        @Param("inicio") OffsetDateTime inicio,
                        @Param("fim") OffsetDateTime fim);

        @Query("SELECT a FROM AplicacaoVacina a WHERE a.tenant.id = :tenantId " +
                        "AND a.estabelecimento.id = :estabelecimentoId " +
                        "AND a.dataAplicacao BETWEEN :inicio AND :fim")
        List<AplicacaoVacina> findByTenantIdAndEstabelecimentoIdAndDataAplicacaoBetween(
                        @Param("tenantId") UUID tenantId,
                        @Param("estabelecimentoId") UUID estabelecimentoId,
                        @Param("inicio") OffsetDateTime inicio,
                        @Param("fim") OffsetDateTime fim);

        @Query("SELECT a FROM AplicacaoVacina a WHERE a.paciente.id = :pacienteId " +
                        "AND a.tenant.id = :tenantId AND a.fhirStatus = :status AND a.ativo = true " +
                        "ORDER BY a.dataAplicacao DESC")
        List<AplicacaoVacina> findByPacienteAndStatus(
                        @Param("pacienteId") UUID pacienteId,
                        @Param("tenantId") UUID tenantId,
                        @Param("status") StatusAplicacao status);

        @Query("SELECT DISTINCT a.imunobiologico.id FROM AplicacaoVacina a " +
                        "WHERE a.paciente.id = :pacienteId AND a.tenant.id = :tenantId AND a.ativo = true")
        List<UUID> findDistinctImunobiologicosByPaciente(
                        @Param("pacienteId") UUID pacienteId,
                        @Param("tenantId") UUID tenantId);

        @Query("SELECT COUNT(a) FROM AplicacaoVacina a WHERE a.tenant.id = :tenantId " +
                        "AND a.dataAplicacao BETWEEN :inicio AND :fim")
        long countByTenantIdAndDataAplicacaoBetween(
                        @Param("tenantId") UUID tenantId,
                        @Param("inicio") OffsetDateTime inicio,
                        @Param("fim") OffsetDateTime fim);

        @Query("SELECT COUNT(a) FROM AplicacaoVacina a WHERE a.paciente.id = :pacienteId " +
                        "AND a.tenant.id = :tenantId")
        long countByPacienteIdAndTenantId(
                        @Param("pacienteId") UUID pacienteId,
                        @Param("tenantId") UUID tenantId);
}
