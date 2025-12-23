package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoSiaSih;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoSiaSihRepository extends JpaRepository<SigtapProcedimentoSiaSih, UUID> {
    
    @Query("SELECT ps FROM SigtapProcedimentoSiaSih ps " +
           "JOIN FETCH ps.siaSih ss " +
           "WHERE ps.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoSiaSih> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
