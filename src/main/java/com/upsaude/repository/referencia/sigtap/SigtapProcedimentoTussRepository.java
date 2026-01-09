package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoTuss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoTussRepository extends JpaRepository<SigtapProcedimentoTuss, UUID> {
    
    @Query("SELECT pt FROM SigtapProcedimentoTuss pt " +
           "JOIN FETCH pt.tuss " +
           "WHERE pt.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoTuss> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
