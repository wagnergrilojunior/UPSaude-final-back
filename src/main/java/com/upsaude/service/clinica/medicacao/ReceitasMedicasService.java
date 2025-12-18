package com.upsaude.service.clinica.medicacao;

import com.upsaude.api.request.clinica.medicacao.ReceitasMedicasRequest;
import com.upsaude.api.response.clinica.medicacao.ReceitasMedicasResponse;
import com.upsaude.enums.StatusReceitaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ReceitasMedicasService {

    ReceitasMedicasResponse criar(ReceitasMedicasRequest request);

    ReceitasMedicasResponse obterPorId(UUID id);

    Page<ReceitasMedicasResponse> listar(Pageable pageable);

    Page<ReceitasMedicasResponse> listar(Pageable pageable,
        UUID estabelecimentoId,
        UUID pacienteId,
        UUID medicoId,
        StatusReceitaEnum status,
        OffsetDateTime inicio,
        OffsetDateTime fim,
        String numeroReceita,
        Boolean usoContinuo,
        String origemReceita,
        UUID cidPrincipalId);

    Page<ReceitasMedicasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ReceitasMedicasResponse atualizar(UUID id, ReceitasMedicasRequest request);

    void excluir(UUID id);
}
