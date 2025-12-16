package com.upsaude.service;

import com.upsaude.api.request.ResponsavelLegalRequest;
import com.upsaude.api.response.ResponsavelLegalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ResponsavelLegalService {

    ResponsavelLegalResponse criar(ResponsavelLegalRequest request);

    ResponsavelLegalResponse obterPorId(UUID id);

    ResponsavelLegalResponse obterPorPacienteId(UUID pacienteId);

    Page<ResponsavelLegalResponse> listar(Pageable pageable, UUID estabelecimentoId, String cpf, String nome);

    ResponsavelLegalResponse atualizar(UUID id, ResponsavelLegalRequest request);

    void excluir(UUID id);
}
