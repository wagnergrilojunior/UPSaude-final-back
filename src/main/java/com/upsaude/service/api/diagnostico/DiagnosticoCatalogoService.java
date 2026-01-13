package com.upsaude.service.api.diagnostico;

import com.upsaude.api.response.diagnostico.Ciap2Response;
import com.upsaude.api.response.diagnostico.Cid10Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface DiagnosticoCatalogoService {
    Page<Cid10Response> listarCid10(String termo, @NonNull Pageable pageable);

    Page<Ciap2Response> listarCiap2(String termo, @NonNull Pageable pageable);

    Cid10Response buscarCid10PorCodigo(String codigo);

    Ciap2Response buscarCiap2PorCodigo(String codigo);
}
