package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapProcedimentoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SigtapProcedimentoServicoRepository extends JpaRepository<SigtapProcedimentoServico, UUID> {
    
    @Query("SELECT ps FROM SigtapProcedimentoServico ps " +
           "JOIN FETCH ps.servicoClassificacao sc " +
           "JOIN FETCH sc.servico " +
           "WHERE ps.procedimento.id = :procedimentoId")
    List<SigtapProcedimentoServico> findByProcedimentoId(@Param("procedimentoId") UUID procedimentoId);
}
