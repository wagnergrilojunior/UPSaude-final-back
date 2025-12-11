package com.upsaude.repository;

import com.upsaude.entity.EscalaTrabalho;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EscalaTrabalhoRepository extends JpaRepository<EscalaTrabalho, UUID> {

    Page<EscalaTrabalho> findByProfissionalIdOrderByDiaSemanaAsc(UUID profissionalId, Pageable pageable);

    Page<EscalaTrabalho> findByMedicoIdOrderByDiaSemanaAsc(UUID medicoId, Pageable pageable);

    Page<EscalaTrabalho> findByEstabelecimentoIdOrderByProfissionalIdAscDiaSemanaAsc(
            UUID estabelecimentoId, Pageable pageable);

    List<EscalaTrabalho> findByProfissionalIdAndDiaSemana(UUID profissionalId, DayOfWeek diaSemana);

    List<EscalaTrabalho> findByEstabelecimentoIdAndDiaSemana(UUID estabelecimentoId, DayOfWeek diaSemana);

    List<EscalaTrabalho> findByProfissionalIdAndDataFimIsNullOrProfissionalIdAndDataFimAfter(
            UUID profissionalId, UUID profissionalId2, LocalDate data);

    Page<EscalaTrabalho> findByTenantOrderByProfissionalIdAscDiaSemanaAsc(Tenant tenant, Pageable pageable);
}
