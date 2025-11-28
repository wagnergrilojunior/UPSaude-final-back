package com.upsaude.service;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a ReceitasMedicas.
 *
 * @author UPSaúde
 */
public interface ReceitasMedicasService {

    ReceitasMedicasResponse criar(ReceitasMedicasRequest request);

    ReceitasMedicasResponse obterPorId(UUID id);

    Page<ReceitasMedicasResponse> listar(Pageable pageable);

    /**
     * Lista todas as receitas de um estabelecimento, ordenadas por data de prescrição decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable Parâmetros de paginação
     * @return Página com as receitas do estabelecimento
     */
    Page<ReceitasMedicasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ReceitasMedicasResponse atualizar(UUID id, ReceitasMedicasRequest request);

    void excluir(UUID id);
}
