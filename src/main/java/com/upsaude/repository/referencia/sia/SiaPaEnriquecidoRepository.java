package com.upsaude.repository.referencia.sia;

import com.upsaude.entity.referencia.sia.SiaPaEnriquecido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface SiaPaEnriquecidoRepository extends JpaRepository<SiaPaEnriquecido, UUID>, JpaSpecificationExecutor<SiaPaEnriquecido> {

    Page<SiaPaEnriquecido> findByCompetenciaAndUf(String competencia, String uf, Pageable pageable);

    Page<SiaPaEnriquecido> findByCompetenciaAndUfAndCodigoCnes(String competencia, String uf, String codigoCnes, Pageable pageable);

    Page<SiaPaEnriquecido> findByCompetenciaAndUfAndProcedimentoCodigo(String competencia, String uf, String procedimentoCodigo, Pageable pageable);
}

