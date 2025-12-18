package com.upsaude.service.clinica.medicacao;

import com.upsaude.api.request.clinica.medicacao.MedicacaoRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MedicacaoService {

    MedicacaoResponse criar(MedicacaoRequest request);

    MedicacaoResponse obterPorId(UUID id);

    Page<MedicacaoResponse> listar(Pageable pageable);

    MedicacaoResponse atualizar(UUID id, MedicacaoRequest request);

    void excluir(UUID id);
}
