package com.upsaude.service.api.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.clinica.atendimento.CheckInAtendimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CheckInAtendimentoService {

    CheckInAtendimentoResponse criar(CheckInAtendimentoRequest request);

    CheckInAtendimentoResponse obterPorId(UUID id);

    Page<CheckInAtendimentoResponse> listar(Pageable pageable);

    CheckInAtendimentoResponse atualizar(UUID id, CheckInAtendimentoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
