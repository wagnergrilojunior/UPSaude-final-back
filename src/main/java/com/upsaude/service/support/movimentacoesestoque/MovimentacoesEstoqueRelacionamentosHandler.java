package com.upsaude.service.support.movimentacoesestoque;

import com.upsaude.api.request.estabelecimento.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.entity.estabelecimento.estoque.MovimentacoesEstoque;
import com.upsaude.entity.saude_publica.vacina.EstoquesVacina;
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

        if (request.getEstoqueVacina() != null) {
            EstoquesVacina estoqueVacina = estoquesVacinaRepository.findById(request.getEstoqueVacina())
                    .orElseThrow(() -> new NotFoundException("Estoque de vacina não encontrado com ID: " + request.getEstoqueVacina()));
            entity.setEstoqueVacina(estoqueVacina);
        }
    }
}
