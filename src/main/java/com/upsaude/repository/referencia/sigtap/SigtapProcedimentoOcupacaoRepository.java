package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOcupacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoOcupacaoRepository extends JpaRepository<SigtapProcedimentoOcupacao, UUID> {
    
    @Query("SELECT po FROM SigtapProcedimentoOcupacao po " +
           "JOIN FETCH po.ocupacao " +
           "WHERE po.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoOcupacao> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
