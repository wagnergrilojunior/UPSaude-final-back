package com.upsaude.service.api.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EquipeCirurgicaService {

    EquipeCirurgicaResponse criar(EquipeCirurgicaRequest request);

    EquipeCirurgicaResponse obterPorId(UUID id);

    Page<EquipeCirurgicaResponse> listar(Pageable pageable);

    Page<EquipeCirurgicaResponse> listarPorCirurgia(UUID cirurgiaId, Pageable pageable);

    EquipeCirurgicaResponse atualizar(UUID id, EquipeCirurgicaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

