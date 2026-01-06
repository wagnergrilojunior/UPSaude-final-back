package com.upsaude.service.api.profissional.equipe;

import com.upsaude.api.request.profissional.equipe.FaltaRequest;
import com.upsaude.api.response.profissional.equipe.FaltaResponse;
import com.upsaude.enums.TipoFaltaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface FaltaService {

    FaltaResponse criar(FaltaRequest request);

    FaltaResponse obterPorId(UUID id);

    Page<FaltaResponse> listar(Pageable pageable,
                              UUID profissionalId,
                              UUID medicoId,
                              UUID estabelecimentoId,
                              TipoFaltaEnum tipoFalta,
                              LocalDate dataInicio,
                              LocalDate dataFim);

    FaltaResponse atualizar(UUID id, FaltaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
