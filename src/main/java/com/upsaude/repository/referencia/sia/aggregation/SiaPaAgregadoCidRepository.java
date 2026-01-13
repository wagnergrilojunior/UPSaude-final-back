package com.upsaude.repository.referencia.sia.aggregation;

import com.upsaude.entity.referencia.sia.aggregation.SiaPaAgregadoCid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiaPaAgregadoCidRepository extends JpaRepository<SiaPaAgregadoCid, String> {

    Page<SiaPaAgregadoCid> findByCompetenciaAndUf(String competencia, String uf, Pageable pageable);
}

