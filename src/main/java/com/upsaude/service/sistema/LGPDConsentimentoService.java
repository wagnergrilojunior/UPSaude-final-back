package com.upsaude.service.sistema;

import com.upsaude.api.request.sistema.LGPDConsentimentoRequest;
import com.upsaude.api.response.sistema.LGPDConsentimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LGPDConsentimentoService {

    LGPDConsentimentoResponse criar(LGPDConsentimentoRequest request);

    LGPDConsentimentoResponse obterPorId(UUID id);

    LGPDConsentimentoResponse obterPorPacienteId(UUID pacienteId);

    Page<LGPDConsentimentoResponse> listar(Pageable pageable);

    LGPDConsentimentoResponse atualizar(UUID id, LGPDConsentimentoRequest request);

    void excluir(UUID id);
}
