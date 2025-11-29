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

/**
 * Repositório para operações de banco de dados relacionadas a EscalaTrabalho.
 *
 * @author UPSaúde
 */
@Repository
public interface EscalaTrabalhoRepository extends JpaRepository<EscalaTrabalho, UUID> {

    /**
     * Busca todas as escalas de um profissional, ordenadas por dia da semana.
     */
    Page<EscalaTrabalho> findByProfissionalIdOrderByDiaSemanaAsc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todas as escalas de um médico, ordenadas por dia da semana.
     */
    Page<EscalaTrabalho> findByMedicoIdOrderByDiaSemanaAsc(UUID medicoId, Pageable pageable);

    /**
     * Busca todas as escalas de um estabelecimento, ordenadas por profissional e dia da semana.
     */
    Page<EscalaTrabalho> findByEstabelecimentoIdOrderByProfissionalIdAscDiaSemanaAsc(
            UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as escalas de um profissional em um dia da semana específico.
     */
    List<EscalaTrabalho> findByProfissionalIdAndDiaSemana(UUID profissionalId, DayOfWeek diaSemana);

    /**
     * Busca todas as escalas de um estabelecimento em um dia da semana específico.
     */
    List<EscalaTrabalho> findByEstabelecimentoIdAndDiaSemana(UUID estabelecimentoId, DayOfWeek diaSemana);

    /**
     * Busca todas as escalas vigentes de um profissional (sem data fim ou data fim no futuro).
     */
    List<EscalaTrabalho> findByProfissionalIdAndDataFimIsNullOrProfissionalIdAndDataFimAfter(
            UUID profissionalId, UUID profissionalId2, LocalDate data);

    /**
     * Busca todas as escalas de um tenant.
     */
    Page<EscalaTrabalho> findByTenantOrderByProfissionalIdAscDiaSemanaAsc(Tenant tenant, Pageable pageable);
}

