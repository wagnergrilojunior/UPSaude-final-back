package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoCid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoCidRepository extends JpaRepository<SigtapProcedimentoCid, UUID> {
    
    @Query("SELECT pc FROM SigtapProcedimentoCid pc " +
           "JOIN FETCH pc.cid10Subcategoria " +
           "WHERE pc.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoCid> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
