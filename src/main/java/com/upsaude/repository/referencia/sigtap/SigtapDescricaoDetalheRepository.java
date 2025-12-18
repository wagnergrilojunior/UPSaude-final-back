package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapDescricaoDetalhe;
import com.upsaude.entity.referencia.sigtap.SigtapDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapDescricaoDetalheRepository extends JpaRepository<SigtapDescricaoDetalhe, UUID> {
    /**
     * Busca uma descrição detalhe por detalhe e competência inicial.
     * Usado para evitar duplicatas durante a importação.
     */
    Optional<SigtapDescricaoDetalhe> findByDetalheAndCompetenciaInicial(
            SigtapDetalhe detalhe, String competenciaInicial);
}
