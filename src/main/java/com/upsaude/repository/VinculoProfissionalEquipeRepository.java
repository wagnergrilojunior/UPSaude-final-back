package com.upsaude.repository;

import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VinculoProfissionalEquipeRepository extends JpaRepository<VinculoProfissionalEquipe, UUID> {

    Optional<VinculoProfissionalEquipe> findByProfissionalIdAndEquipeId(UUID profissionalId, UUID equipeId);

    Page<VinculoProfissionalEquipe> findByProfissionalIdOrderByDataInicioDesc(UUID profissionalId, Pageable pageable);

    Page<VinculoProfissionalEquipe> findByEquipeIdOrderByDataInicioDesc(UUID equipeId, Pageable pageable);

    Page<VinculoProfissionalEquipe> findByTenant(Tenant tenant, Pageable pageable);

    Page<VinculoProfissionalEquipe> findByTipoVinculoAndEquipeId(TipoVinculoProfissionalEnum tipoVinculo, UUID equipeId, Pageable pageable);

    Page<VinculoProfissionalEquipe> findByStatusAndEquipeId(StatusAtivoEnum status, UUID equipeId, Pageable pageable);

    List<VinculoProfissionalEquipe> findByEquipeIdAndDataFimIsNull(UUID equipeId);

    List<VinculoProfissionalEquipe> findByProfissionalIdAndDataFimIsNull(UUID profissionalId);

    @org.springframework.data.jpa.repository.Query(
            "SELECT v FROM VinculoProfissionalEquipe v WHERE v.equipe.id = :equipeId " +
            "AND v.dataInicio <= :dataFimPeriodo " +
            "AND (v.dataFim IS NULL OR v.dataFim >= :dataInicioPeriodo) " +
            "ORDER BY v.dataInicio DESC")
    List<VinculoProfissionalEquipe> findByEquipeIdAndPeriodoAtivo(
            @org.springframework.data.repository.query.Param("equipeId") UUID equipeId,
            @org.springframework.data.repository.query.Param("dataInicioPeriodo") OffsetDateTime dataInicioPeriodo,
            @org.springframework.data.repository.query.Param("dataFimPeriodo") OffsetDateTime dataFimPeriodo);
}
