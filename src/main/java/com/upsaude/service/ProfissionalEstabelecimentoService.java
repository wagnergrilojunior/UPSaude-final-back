package com.upsaude.service;

import com.upsaude.api.request.ProfissionalEstabelecimentoRequest;
import com.upsaude.api.response.ProfissionalEstabelecimentoResponse;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Vínculos de Profissionais com Estabelecimentos.
 *
 * @author UPSaúde
 */
public interface ProfissionalEstabelecimentoService {

    ProfissionalEstabelecimentoResponse criar(ProfissionalEstabelecimentoRequest request);

    ProfissionalEstabelecimentoResponse obterPorId(UUID id);

    Page<ProfissionalEstabelecimentoResponse> listar(Pageable pageable);

    Page<ProfissionalEstabelecimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    Page<ProfissionalEstabelecimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<ProfissionalEstabelecimentoResponse> listarPorTipoVinculo(TipoVinculoProfissionalEnum tipoVinculo, UUID estabelecimentoId, Pageable pageable);

    ProfissionalEstabelecimentoResponse atualizar(UUID id, ProfissionalEstabelecimentoRequest request);

    void excluir(UUID id);
}

