package com.upsaude.repository;

import com.upsaude.entity.ControlePonto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a ControlePonto.
 *
 * @author UPSaúde
 */
@Repository
public interface ControlePontoRepository extends JpaRepository<ControlePonto, UUID> {

    /**
     * Busca todos os registros de ponto de um profissional, ordenados por data/hora decrescente.
     */
    Page<ControlePonto> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todos os registros de ponto de um médico, ordenados por data/hora decrescente.
     */
    Page<ControlePonto> findByMedicoIdOrderByDataHoraDesc(UUID medicoId, Pageable pageable);

    /**
     * Busca todos os registros de ponto de um estabelecimento, ordenados por data/hora decrescente.
     */
    Page<ControlePonto> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os registros de ponto de um profissional em uma data específica.
     */
    Page<ControlePonto> findByProfissionalIdAndDataPontoOrderByDataHoraAsc(UUID profissionalId, LocalDate dataPonto, Pageable pageable);

    /**
     * Busca todos os registros de ponto de um profissional em um período.
     */
    Page<ControlePonto> findByProfissionalIdAndDataPontoBetweenOrderByDataHoraAsc(UUID profissionalId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    /**
     * Busca todos os registros de ponto de um estabelecimento em um período.
     */
    Page<ControlePonto> findByEstabelecimentoIdAndDataPontoBetweenOrderByDataHoraAsc(UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);
}

