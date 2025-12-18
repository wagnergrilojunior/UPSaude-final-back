package com.upsaude.service.support.movimentacoesestoque;

import com.upsaude.api.request.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.entity.vacina.EstoquesVacina;
import com.upsaude.entity.estoque.MovimentacoesEstoque;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.vacina.EstoquesVacinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueRelacionamentosHandler {

    private final EstoquesVacinaRepository estoquesVacinaRepository;

    public void resolver(MovimentacoesEstoque entity, MovimentacoesEstoqueRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        UUID estoqueVacinaId = Objects.requireNonNull(request.getEstoqueVacina(), "estoqueVacina");
        EstoquesVacina estoqueVacina = estoquesVacinaRepository.findByIdAndTenant(estoqueVacinaId, tenantId)
            .orElseThrow(() -> new NotFoundException("Estoque de vacina não encontrado com ID: " + estoqueVacinaId));
        entity.setEstoqueVacina(estoqueVacina);

        if (estoqueVacina.getEstabelecimento() != null) {
            entity.setEstabelecimento(estoqueVacina.getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
