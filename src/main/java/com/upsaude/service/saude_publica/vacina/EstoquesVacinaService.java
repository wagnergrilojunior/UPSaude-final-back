package com.upsaude.service.saude_publica.vacina;

import com.upsaude.api.request.saude_publica.vacina.EstoquesVacinaRequest;
import com.upsaude.api.response.saude_publica.vacina.EstoquesVacinaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EstoquesVacinaService {

    EstoquesVacinaResponse criar(EstoquesVacinaRequest request);

    EstoquesVacinaResponse obterPorId(UUID id);

    Page<EstoquesVacinaResponse> listar(Pageable pageable);

    EstoquesVacinaResponse atualizar(UUID id, EstoquesVacinaRequest request);

    void excluir(UUID id);
}
