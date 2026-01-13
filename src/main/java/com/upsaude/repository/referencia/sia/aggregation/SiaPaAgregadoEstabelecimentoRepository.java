package com.upsaude.repository.referencia.sia.aggregation;

import com.upsaude.entity.referencia.sia.aggregation.SiaPaAgregadoEstabelecimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiaPaAgregadoEstabelecimentoRepository extends JpaRepository<SiaPaAgregadoEstabelecimento, String> {

    Page<SiaPaAgregadoEstabelecimento> findByCompetenciaAndUf(String competencia, String uf, Pageable pageable);
}

