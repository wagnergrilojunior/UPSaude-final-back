package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapDescricao;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SigtapDescricaoRepository extends JpaRepository<SigtapDescricao, UUID> {
    Optional<SigtapDescricao> findByProcedimentoAndCompetenciaInicial(
            SigtapProcedimento procedimento, String competenciaInicial);
    
    @Query("SELECT d FROM SigtapDescricao d WHERE d.procedimento.id = :procedimentoId ORDER BY d.competenciaInicial DESC")
    List<SigtapDescricao> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
