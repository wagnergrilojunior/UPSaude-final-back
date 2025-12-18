package com.upsaude.repository.clinica.cirurgia;

import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.enums.StatusCirurgiaEnum;
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
public interface CirurgiaRepository extends JpaRepository<Cirurgia, UUID> {

    Page<Cirurgia> findByPacienteIdOrderByDataHoraPrevistaDesc(UUID pacienteId, Pageable pageable);

    Page<Cirurgia> findByCirurgiaoPrincipalIdOrderByDataHoraPrevistaAsc(UUID cirurgiaoPrincipalId, Pageable pageable);

    Page<Cirurgia> findByEstabelecimentoIdOrderByDataHoraPrevistaAsc(UUID estabelecimentoId, Pageable pageable);

    Page<Cirurgia> findByStatusOrderByDataHoraPrevistaAsc(StatusCirurgiaEnum status, Pageable pageable);

    Page<Cirurgia> findByCirurgiaoPrincipalIdAndDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            UUID cirurgiaoPrincipalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Cirurgia> findByEstabelecimentoIdAndDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Cirurgia> findByCirurgiaoPrincipalIdAndStatusOrderByDataHoraPrevistaAsc(
            UUID cirurgiaoPrincipalId, StatusCirurgiaEnum status, Pageable pageable);

    Page<Cirurgia> findByDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Cirurgia> findByTenantOrderByDataHoraPrevistaDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT c FROM Cirurgia c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<Cirurgia> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM Cirurgia c WHERE c.tenant.id = :tenantId")
    Page<Cirurgia> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM Cirurgia c WHERE c.paciente.id = :pacienteId AND c.tenant.id = :tenantId ORDER BY c.dataHoraPrevista DESC")
    Page<Cirurgia> findByPacienteIdAndTenantIdOrderByDataHoraPrevistaDesc(
        @Param("pacienteId") UUID pacienteId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT c FROM Cirurgia c WHERE c.cirurgiaoPrincipal.id = :cirurgiaoPrincipalId AND c.tenant.id = :tenantId ORDER BY c.dataHoraPrevista ASC")
    Page<Cirurgia> findByCirurgiaoPrincipalIdAndTenantIdOrderByDataHoraPrevistaAsc(
        @Param("cirurgiaoPrincipalId") UUID cirurgiaoPrincipalId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT c FROM Cirurgia c WHERE c.estabelecimento.id = :estabelecimentoId AND c.tenant.id = :tenantId ORDER BY c.dataHoraPrevista ASC")
    Page<Cirurgia> findByEstabelecimentoIdAndTenantIdOrderByDataHoraPrevistaAsc(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT c FROM Cirurgia c WHERE c.status = :status AND c.tenant.id = :tenantId ORDER BY c.dataHoraPrevista ASC")
    Page<Cirurgia> findByStatusAndTenantIdOrderByDataHoraPrevistaAsc(
        @Param("status") StatusCirurgiaEnum status,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT c FROM Cirurgia c WHERE c.dataHoraPrevista BETWEEN :inicio AND :fim AND c.tenant.id = :tenantId ORDER BY c.dataHoraPrevista ASC")
    Page<Cirurgia> findByDataHoraPrevistaBetweenAndTenantIdOrderByDataHoraPrevistaAsc(
        @Param("inicio") OffsetDateTime inicio,
        @Param("fim") OffsetDateTime fim,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);
}
