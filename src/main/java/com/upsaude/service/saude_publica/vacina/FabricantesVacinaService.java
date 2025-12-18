package com.upsaude.service.saude_publica.vacina;

import com.upsaude.api.request.saude_publica.vacina.FabricantesVacinaRequest;
import com.upsaude.api.response.saude_publica.vacina.FabricantesVacinaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FabricantesVacinaService {

    FabricantesVacinaResponse criar(FabricantesVacinaRequest request);

    FabricantesVacinaResponse obterPorId(UUID id);

    Page<FabricantesVacinaResponse> listar(Pageable pageable);

    FabricantesVacinaResponse atualizar(UUID id, FabricantesVacinaRequest request);

    void excluir(UUID id);
}
