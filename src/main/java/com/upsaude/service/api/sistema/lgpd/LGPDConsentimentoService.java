package com.upsaude.service.api.sistema.lgpd;

import com.upsaude.api.request.sistema.lgpd.LGPDConsentimentoRequest;
import com.upsaude.api.response.sistema.lgpd.LGPDConsentimentoResponse;
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

    void inativar(UUID id);
}
