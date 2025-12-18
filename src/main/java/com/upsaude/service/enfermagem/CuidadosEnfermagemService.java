package com.upsaude.service.enfermagem;

import com.upsaude.api.request.enfermagem.CuidadosEnfermagemRequest;
import com.upsaude.api.response.enfermagem.CuidadosEnfermagemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

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
