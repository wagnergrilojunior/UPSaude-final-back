package com.upsaude.service;

import com.upsaude.api.request.DoencasCronicasRequest;
import com.upsaude.api.response.DoencasCronicasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a DoencasCronicas.
 *
 * @author UPSaúde
 */
public interface DoencasCronicasService {

    DoencasCronicasResponse criar(DoencasCronicasRequest request);

    DoencasCronicasResponse obterPorId(UUID id);

    Page<DoencasCronicasResponse> listar(Pageable pageable);

    DoencasCronicasResponse atualizar(UUID id, DoencasCronicasRequest request);

    void excluir(UUID id);
}
