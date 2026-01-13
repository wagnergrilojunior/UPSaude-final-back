package com.upsaude.repository.referencia.sia.anomalia;

import com.upsaude.entity.referencia.sia.anomalia.SiaPaAnomalia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SiaPaAnomaliaRepository extends JpaRepository<SiaPaAnomalia, UUID> {

    Page<SiaPaAnomalia> findByCompetenciaAndUf(String competencia, String uf, Pageable pageable);
}

