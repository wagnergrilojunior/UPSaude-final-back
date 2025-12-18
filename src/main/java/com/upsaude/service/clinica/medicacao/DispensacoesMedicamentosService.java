package com.upsaude.service.clinica.medicacao;

import com.upsaude.api.request.clinica.medicacao.DispensacoesMedicamentosRequest;
import com.upsaude.api.response.clinica.medicacao.DispensacoesMedicamentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DispensacoesMedicamentosService {

    DispensacoesMedicamentosResponse criar(DispensacoesMedicamentosRequest request);

    DispensacoesMedicamentosResponse obterPorId(UUID id);

    Page<DispensacoesMedicamentosResponse> listar(Pageable pageable);

    Page<DispensacoesMedicamentosResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    DispensacoesMedicamentosResponse atualizar(UUID id, DispensacoesMedicamentosRequest request);

    void excluir(UUID id);
}
