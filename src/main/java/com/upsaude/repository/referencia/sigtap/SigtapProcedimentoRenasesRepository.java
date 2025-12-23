package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRenases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoRenasesRepository extends JpaRepository<SigtapProcedimentoRenases, UUID> {
    
    @Query("SELECT pr FROM SigtapProcedimentoRenases pr " +
           "JOIN FETCH pr.renases r " +
           "WHERE pr.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoRenases> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
