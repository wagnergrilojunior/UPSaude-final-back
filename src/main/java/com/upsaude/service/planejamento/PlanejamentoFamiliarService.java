package com.upsaude.service.planejamento;

import com.upsaude.api.request.planejamento.PlanejamentoFamiliarRequest;
import com.upsaude.api.response.planejamento.PlanejamentoFamiliarResponse;
import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PlanejamentoFamiliarService {

    PlanejamentoFamiliarResponse criar(PlanejamentoFamiliarRequest request);

    PlanejamentoFamiliarResponse obterPorId(UUID id);

    Page<PlanejamentoFamiliarResponse> listar(Pageable pageable);

    Page<PlanejamentoFamiliarResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    List<PlanejamentoFamiliarResponse> listarPorPaciente(UUID pacienteId);

    Page<PlanejamentoFamiliarResponse> listarAtivos(UUID estabelecimentoId, Pageable pageable);

    Page<PlanejamentoFamiliarResponse> listarPorMetodo(UUID estabelecimentoId, TipoMetodoContraceptivoEnum metodo, Pageable pageable);

    PlanejamentoFamiliarResponse obterAtivoPorPaciente(UUID pacienteId);

    PlanejamentoFamiliarResponse atualizar(UUID id, PlanejamentoFamiliarRequest request);

    void excluir(UUID id);
}
