package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoOrigem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoOrigemRepository extends JpaRepository<SigtapProcedimentoOrigem, UUID> {
    
    @Query("SELECT po FROM SigtapProcedimentoOrigem po " +
           "JOIN FETCH po.procedimentoOrigem p_origem " +
           "WHERE po.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoOrigem> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
