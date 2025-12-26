package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoRegistroRepository extends JpaRepository<SigtapProcedimentoRegistro, UUID> {
    
    @Query("SELECT pr FROM SigtapProcedimentoRegistro pr " +
           "JOIN FETCH pr.registro r " +
           "WHERE pr.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoRegistro> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
