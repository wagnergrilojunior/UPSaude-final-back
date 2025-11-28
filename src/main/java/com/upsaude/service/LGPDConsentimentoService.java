package com.upsaude.service;

import com.upsaude.api.request.LGPDConsentimentoRequest;
import com.upsaude.api.response.LGPDConsentimentoResponse;
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

