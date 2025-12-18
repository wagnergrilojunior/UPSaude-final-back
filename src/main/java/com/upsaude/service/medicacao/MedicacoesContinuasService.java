package com.upsaude.service.medicacao;

import com.upsaude.api.request.medicacao.MedicacoesContinuasRequest;
import com.upsaude.api.response.medicacao.MedicacoesContinuasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MedicacoesContinuasService {

    MedicacoesContinuasResponse criar(MedicacoesContinuasRequest request);

    MedicacoesContinuasResponse obterPorId(UUID id);

    Page<MedicacoesContinuasResponse> listar(Pageable pageable);

    MedicacoesContinuasResponse atualizar(UUID id, MedicacoesContinuasRequest request);

    void excluir(UUID id);
}
