package com.upsaude.service.api.clinica.prontuario;

import com.upsaude.api.request.clinica.prontuario.ProntuarioRequest;
import com.upsaude.api.response.clinica.prontuario.ProntuarioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProntuarioService {

    ProntuarioResponse criar(ProntuarioRequest request);

    ProntuarioResponse obterPorId(UUID id);

    Page<ProntuarioResponse> listar(Pageable pageable);

    Page<ProntuarioResponse> listar(Pageable pageable, UUID pacienteId, UUID estabelecimentoId, UUID criadoPor);

    Page<ProntuarioResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ProntuarioResponse atualizar(UUID id, ProntuarioRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

