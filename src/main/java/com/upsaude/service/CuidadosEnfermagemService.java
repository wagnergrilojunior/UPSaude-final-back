package com.upsaude.service;

import com.upsaude.api.request.CuidadosEnfermagemRequest;
import com.upsaude.api.response.CuidadosEnfermagemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações relacionadas a Cuidados de Enfermagem.
 *
 * @author UPSaúde
 */
public interface CuidadosEnfermagemService {

    CuidadosEnfermagemResponse criar(CuidadosEnfermagemRequest request);

    CuidadosEnfermagemResponse obterPorId(UUID id);

    Page<CuidadosEnfermagemResponse> listar(Pageable pageable);

    Page<CuidadosEnfermagemResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<CuidadosEnfermagemResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<CuidadosEnfermagemResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    CuidadosEnfermagemResponse atualizar(UUID id, CuidadosEnfermagemRequest request);

    void excluir(UUID id);
}

