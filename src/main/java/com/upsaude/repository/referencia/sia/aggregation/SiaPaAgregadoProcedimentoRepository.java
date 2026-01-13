package com.upsaude.repository.referencia.sia.aggregation;

import com.upsaude.entity.referencia.sia.aggregation.SiaPaAgregadoProcedimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiaPaAgregadoProcedimentoRepository extends JpaRepository<SiaPaAgregadoProcedimento, String> {

    Page<SiaPaAgregadoProcedimento> findByCompetenciaAndUf(String competencia, String uf, Pageable pageable);
}

