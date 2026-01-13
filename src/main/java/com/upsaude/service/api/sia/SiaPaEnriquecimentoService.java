package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.SiaPaEnriquecidoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SiaPaEnriquecimentoService {

    Page<SiaPaEnriquecidoResponse> listarEnriquecido(String competencia, String uf, String codigoCnes, String procedimentoCodigo, Pageable pageable);

    SiaPaEnriquecidoResponse obterPorId(UUID id);
}

