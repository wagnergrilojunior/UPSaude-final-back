package com.upsaude.service.prontuario;

import com.upsaude.api.request.prontuario.ProntuariosRequest;
import com.upsaude.api.response.prontuario.ProntuariosResponse;
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
