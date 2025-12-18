package com.upsaude.service.profissional.equipe;

import com.upsaude.api.request.profissional.equipe.VinculoProfissionalEquipeRequest;
import com.upsaude.api.response.profissional.equipe.VinculoProfissionalEquipeResponse;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface VinculoProfissionalEquipeService {

    VinculoProfissionalEquipeResponse criar(VinculoProfissionalEquipeRequest request);

    VinculoProfissionalEquipeResponse obterPorId(UUID id);

    Page<VinculoProfissionalEquipeResponse> listar(Pageable pageable);

    Page<VinculoProfissionalEquipeResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    Page<VinculoProfissionalEquipeResponse> listarPorEquipe(UUID equipeId, Pageable pageable);

    Page<VinculoProfissionalEquipeResponse> listarPorTipoVinculo(TipoVinculoProfissionalEnum tipoVinculo, UUID equipeId, Pageable pageable);

    Page<VinculoProfissionalEquipeResponse> listarPorStatus(StatusAtivoEnum status, UUID equipeId, Pageable pageable);

    VinculoProfissionalEquipeResponse atualizar(UUID id, VinculoProfissionalEquipeRequest request);

    void excluir(UUID id);
}
