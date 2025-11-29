package com.upsaude.repository;

import com.upsaude.entity.Falta;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoFaltaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Falta.
 *
 * @author UPSaúde
 */
@Repository
public interface FaltaRepository extends JpaRepository<Falta, UUID> {

    /**
     * Busca todas as faltas de um profissional, ordenadas por data decrescente.
     */
    Page<Falta> findByProfissionalIdOrderByDataFaltaDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todas as faltas de um médico, ordenadas por data decrescente.
     */
    Page<Falta> findByMedicoIdOrderByDataFaltaDesc(UUID medicoId, Pageable pageable);

    /**
     * Busca todas as faltas de um estabelecimento, ordenadas por data decrescente.
     */
    Page<Falta> findByEstabelecimentoIdOrderByDataFaltaDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as faltas por tipo, ordenadas por data decrescente.
     */
    Page<Falta> findByTipoFaltaOrderByDataFaltaDesc(TipoFaltaEnum tipoFalta, Pageable pageable);

    /**
     * Busca todas as faltas de um profissional em um período.
     */
    Page<Falta> findByProfissionalIdAndDataFaltaBetweenOrderByDataFaltaDesc(
            UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    /**
     * Busca todas as faltas de um estabelecimento em um período.
     */
    Page<Falta> findByEstabelecimentoIdAndDataFaltaBetweenOrderByDataFaltaDesc(
            UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    /**
     * Busca todas as faltas de um profissional por tipo.
     */
    Page<Falta> findByProfissionalIdAndTipoFaltaOrderByDataFaltaDesc(
            UUID profissionalId, TipoFaltaEnum tipoFalta, Pageable pageable);

    /**
     * Busca todas as faltas de um tenant.
     */
    Page<Falta> findByTenantOrderByDataFaltaDesc(Tenant tenant, Pageable pageable);
}

