package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapDescricao;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapDescricaoRepository extends JpaRepository<SigtapDescricao, UUID> {
    /**
     * Busca uma descrição por procedimento e competência inicial.
     * Usado para evitar duplicatas durante a importação.
     */
    Optional<SigtapDescricao> findByProcedimentoAndCompetenciaInicial(
            SigtapProcedimento procedimento, String competenciaInicial);
}
