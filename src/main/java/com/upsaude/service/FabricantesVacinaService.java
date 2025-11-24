package com.upsaude.service;

import com.upsaude.api.request.FabricantesVacinaRequest;
import com.upsaude.api.response.FabricantesVacinaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a FabricantesVacina.
 *
 * @author UPSaúde
 */
public interface FabricantesVacinaService {

    FabricantesVacinaResponse criar(FabricantesVacinaRequest request);

    FabricantesVacinaResponse obterPorId(UUID id);

    Page<FabricantesVacinaResponse> listar(Pageable pageable);

    FabricantesVacinaResponse atualizar(UUID id, FabricantesVacinaRequest request);

    void excluir(UUID id);
}
