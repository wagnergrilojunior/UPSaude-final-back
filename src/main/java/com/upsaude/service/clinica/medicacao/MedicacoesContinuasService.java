package com.upsaude.service.clinica.medicacao;

import com.upsaude.api.request.clinica.medicacao.MedicacoesContinuasRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacoesContinuasResponse;
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
