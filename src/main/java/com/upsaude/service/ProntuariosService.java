package com.upsaude.service;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProntuariosService {

    ProntuariosResponse criar(ProntuariosRequest request);

    ProntuariosResponse obterPorId(UUID id);

    Page<ProntuariosResponse> listar(Pageable pageable);

    Page<ProntuariosResponse> listar(Pageable pageable, UUID pacienteId, UUID estabelecimentoId, String tipoRegistro, UUID criadoPor);

    Page<ProntuariosResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ProntuariosResponse atualizar(UUID id, ProntuariosRequest request);

    void excluir(UUID id);
}
