package com.upsaude.repository.referencia.sia.aggregation;

import com.upsaude.entity.referencia.sia.aggregation.SiaPaAgregadoTemporal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiaPaAgregadoTemporalRepository extends JpaRepository<SiaPaAgregadoTemporal, String> {

    Page<SiaPaAgregadoTemporal> findByUf(String uf, Pageable pageable);

    Page<SiaPaAgregadoTemporal> findByUfAndCompetenciaBetween(String uf, String competenciaInicio, String competenciaFim, Pageable pageable);
}

