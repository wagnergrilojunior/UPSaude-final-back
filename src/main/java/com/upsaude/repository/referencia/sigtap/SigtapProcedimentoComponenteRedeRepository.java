package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoComponenteRede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoComponenteRedeRepository extends JpaRepository<SigtapProcedimentoComponenteRede, UUID> {
    
    @Query("SELECT pcr FROM SigtapProcedimentoComponenteRede pcr " +
           "JOIN FETCH pcr.componenteRede " +
           "WHERE pcr.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoComponenteRede> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
