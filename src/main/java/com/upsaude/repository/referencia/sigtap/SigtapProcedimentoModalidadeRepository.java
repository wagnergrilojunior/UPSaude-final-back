package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoModalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoModalidadeRepository extends JpaRepository<SigtapProcedimentoModalidade, UUID> {
    @Query("SELECT pm FROM SigtapProcedimentoModalidade pm " +
           "LEFT JOIN FETCH pm.modalidade " +
           "WHERE pm.procedimento.id = :procedimentoId " +
           "ORDER BY pm.competenciaInicial DESC")
    List<SigtapProcedimentoModalidade> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
