package com.upsaude.service;

import com.upsaude.api.request.AtividadeProfissionalRequest;
import com.upsaude.api.response.AtividadeProfissionalResponse;
import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AtividadeProfissionalService {

    AtividadeProfissionalResponse criar(AtividadeProfissionalRequest request);

    AtividadeProfissionalResponse obterPorId(UUID id);

    Page<AtividadeProfissionalResponse> listar(Pageable pageable,
                                              UUID profissionalId,
                                              UUID medicoId,
                                              UUID estabelecimentoId,
                                              TipoAtividadeProfissionalEnum tipoAtividade,
                                              OffsetDateTime dataInicio,
                                              OffsetDateTime dataFim);

    AtividadeProfissionalResponse atualizar(UUID id, AtividadeProfissionalRequest request);

    void excluir(UUID id);
}

