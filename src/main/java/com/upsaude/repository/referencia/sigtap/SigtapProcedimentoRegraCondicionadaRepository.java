package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRegraCondicionada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoRegraCondicionadaRepository extends JpaRepository<SigtapProcedimentoRegraCondicionada, UUID> {
    
    @Query("SELECT prc FROM SigtapProcedimentoRegraCondicionada prc " +
           "JOIN FETCH prc.regraCondicionada " +
           "WHERE prc.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoRegraCondicionada> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
