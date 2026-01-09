package com.upsaude.repository.profissional.equipe;

import com.upsaude.entity.profissional.equipe.EscalaTrabalho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface EscalaTrabalhoRepository extends JpaRepository<EscalaTrabalho, UUID> {

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.id = :id AND e.tenant.id = :tenantId")
    java.util.Optional<EscalaTrabalho> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.tenant.id = :tenantId")
    Page<EscalaTrabalho> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.profissional.id = :profissionalId AND e.tenant.id = :tenantId ORDER BY e.diaSemana ASC, e.horaEntrada ASC")
    Page<EscalaTrabalho> findByProfissionalIdAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(@Param("profissionalId") UUID profissionalId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.medico.id = :medicoId AND e.tenant.id = :tenantId ORDER BY e.diaSemana ASC, e.horaEntrada ASC")
    Page<EscalaTrabalho> findByMedicoIdAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(@Param("medicoId") UUID medicoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.estabelecimento.id = :estabelecimentoId AND e.tenant.id = :tenantId ORDER BY e.diaSemana ASC, e.horaEntrada ASC")
    Page<EscalaTrabalho> findByEstabelecimentoIdAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.diaSemana = :diaSemana AND e.tenant.id = :tenantId ORDER BY e.horaEntrada ASC")
    Page<EscalaTrabalho> findByDiaSemanaAndTenantIdOrderByHoraInicioAsc(@Param("diaSemana") DayOfWeek diaSemana, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.dataInicio <= :data AND (e.dataFim IS NULL OR e.dataFim >= :data) AND e.tenant.id = :tenantId ORDER BY e.diaSemana ASC, e.horaEntrada ASC")
    Page<EscalaTrabalho> findVigentesEmAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(@Param("data") LocalDate data, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.active = true AND e.tenant.id = :tenantId ORDER BY e.diaSemana ASC, e.horaEntrada ASC")
    Page<EscalaTrabalho> findByActiveTrueAndTenantIdOrderByDiaSemanaAscHoraInicioAsc(@Param("tenantId") UUID tenantId, Pageable pageable);
}
