package com.upsaude.repository;

import com.upsaude.entity.EscalaTrabalho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EscalaTrabalhoRepository extends JpaRepository<EscalaTrabalho, UUID> {

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<EscalaTrabalho> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM EscalaTrabalho e WHERE e.tenant.id = :tenantId")
    Page<EscalaTrabalho> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("""
        SELECT e FROM EscalaTrabalho e
        WHERE e.tenant.id = :tenantId
          AND (:profissionalId IS NULL OR e.profissional.id = :profissionalId)
          AND (:medicoId IS NULL OR e.medico.id = :medicoId)
          AND (:estabelecimentoId IS NULL OR e.estabelecimento.id = :estabelecimentoId)
          AND (:diaSemana IS NULL OR e.diaSemana = :diaSemana)
          AND (:apenasAtivos IS NULL OR e.active = :apenasAtivos)
          AND (:vigentesEm IS NULL OR ((e.dataInicio IS NULL OR e.dataInicio <= :vigentesEm) AND (e.dataFim IS NULL OR e.dataFim >= :vigentesEm)))
        ORDER BY e.profissional.id ASC, e.diaSemana ASC
        """)
    Page<EscalaTrabalho> filtrar(
        @Param("tenantId") UUID tenantId,
        @Param("profissionalId") UUID profissionalId,
        @Param("medicoId") UUID medicoId,
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("diaSemana") DayOfWeek diaSemana,
        @Param("vigentesEm") LocalDate vigentesEm,
        @Param("apenasAtivos") Boolean apenasAtivos,
        Pageable pageable
    );

    Page<EscalaTrabalho> findByProfissionalIdAndTenantIdOrderByDiaSemanaAsc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<EscalaTrabalho> findByMedicoIdAndTenantIdOrderByDiaSemanaAsc(UUID medicoId, UUID tenantId, Pageable pageable);

    Page<EscalaTrabalho> findByEstabelecimentoIdAndTenantIdOrderByProfissionalIdAscDiaSemanaAsc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    List<EscalaTrabalho> findByProfissionalIdAndDiaSemanaAndTenantId(UUID profissionalId, DayOfWeek diaSemana, UUID tenantId);

    List<EscalaTrabalho> findByEstabelecimentoIdAndDiaSemanaAndTenantId(UUID estabelecimentoId, DayOfWeek diaSemana, UUID tenantId);
}
