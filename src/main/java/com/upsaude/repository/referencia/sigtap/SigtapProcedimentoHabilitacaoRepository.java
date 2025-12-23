package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoHabilitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoHabilitacaoRepository extends JpaRepository<SigtapProcedimentoHabilitacao, UUID> {
    
    @Query("SELECT ph FROM SigtapProcedimentoHabilitacao ph " +
           "JOIN FETCH ph.habilitacao " +
           "LEFT JOIN FETCH ph.grupoHabilitacao " +
           "WHERE ph.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoHabilitacao> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
