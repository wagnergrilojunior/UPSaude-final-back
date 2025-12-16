package com.upsaude.service;

import com.upsaude.api.request.PlantaoRequest;
import com.upsaude.api.response.PlantaoResponse;
import com.upsaude.enums.TipoPlantaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface PlantaoService {

    PlantaoResponse criar(PlantaoRequest request);

    PlantaoResponse obterPorId(UUID id);

    PlantaoResponse atualizar(UUID id, PlantaoRequest request);

    void excluir(UUID id);

    Page<PlantaoResponse> listar(Pageable pageable,
                                UUID profissionalId,
                                UUID medicoId,
                                UUID estabelecimentoId,
                                TipoPlantaoEnum tipoPlantao,
                                OffsetDateTime dataInicio,
                                OffsetDateTime dataFim,
                                Boolean emAndamento);
}

