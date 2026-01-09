package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoLeito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoLeitoRepository extends JpaRepository<SigtapProcedimentoLeito, UUID> {
    
    @Query("SELECT pl FROM SigtapProcedimentoLeito pl " +
           "JOIN FETCH pl.tipoLeito " +
           "WHERE pl.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoLeito> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
