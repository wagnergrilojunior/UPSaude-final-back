package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.anomalia.SiaPaAnomaliaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SiaPaAnomaliaDetectionService {

    /**
     * Executa detecção para um recorte (competência/UF).
     * Retorna o total de anomalias inseridas (aproximado).
     */
    int detectar(String competencia, String uf);

    Page<SiaPaAnomaliaResponse> listar(String competencia, String uf, Pageable pageable);

    SiaPaAnomaliaResponse obterPorId(UUID id);
}

