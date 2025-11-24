package com.upsaude.service;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.api.response.VacinasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Vacinas.
 *
 * @author UPSaúde
 */
public interface VacinasService {

    VacinasResponse criar(VacinasRequest request);

    VacinasResponse obterPorId(UUID id);

    Page<VacinasResponse> listar(Pageable pageable);

    VacinasResponse atualizar(UUID id, VacinasRequest request);

    void excluir(UUID id);
}
