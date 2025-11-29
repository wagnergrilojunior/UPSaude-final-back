package com.upsaude.repository;

import com.upsaude.entity.Plantao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoPlantaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Plantao.
 *
 * @author UPSaúde
 */
@Repository
public interface PlantaoRepository extends JpaRepository<Plantao, UUID> {

    /**
     * Busca todos os plantões de um profissional, ordenados por data/hora de início decrescente.
     */
    Page<Plantao> findByProfissionalIdOrderByDataHoraInicioDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todos os plantões de um médico, ordenados por data/hora de início decrescente.
     */
    Page<Plantao> findByMedicoIdOrderByDataHoraInicioDesc(UUID medicoId, Pageable pageable);

    /**
     * Busca todos os plantões de um estabelecimento, ordenados por data/hora de início decrescente.
     */
    Page<Plantao> findByEstabelecimentoIdOrderByDataHoraInicioDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os plantões por tipo, ordenados por data/hora de início decrescente.
     */
    Page<Plantao> findByTipoPlantaoOrderByDataHoraInicioDesc(TipoPlantaoEnum tipoPlantao, Pageable pageable);

    /**
     * Busca todos os plantões de um profissional em um período.
     */
    Page<Plantao> findByProfissionalIdAndDataHoraInicioBetweenOrderByDataHoraInicioAsc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todos os plantões de um estabelecimento em um período.
     */
    Page<Plantao> findByEstabelecimentoIdAndDataHoraInicioBetweenOrderByDataHoraInicioAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca plantões ativos (que estão acontecendo no momento).
     */
    List<Plantao> findByProfissionalIdAndDataHoraInicioLessThanEqualAndDataHoraFimGreaterThanEqual(
            UUID profissionalId, OffsetDateTime agora, OffsetDateTime agora2);

    /**
     * Busca todos os plantões de um tenant.
     */
    Page<Plantao> findByTenantOrderByDataHoraInicioDesc(Tenant tenant, Pageable pageable);
}

