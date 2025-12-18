package com.upsaude.service.support.movimentacoesestoque;

import com.upsaude.api.request.estabelecimento.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class MovimentacoesEstoqueValidationService {

    public void validarObrigatorios(MovimentacoesEstoqueRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da movimentação de estoque são obrigatórios");
        }
        if (request.getEstoqueVacina() == null) {
            throw new BadRequestException("Estoque de vacina é obrigatório");
        }
        if (request.getTipoMovimento() == null || request.getTipoMovimento().isBlank()) {
            throw new BadRequestException("Tipo de movimentação é obrigatório");
        }
        if (request.getQuantidade() == null) {
            throw new BadRequestException("Quantidade é obrigatória");
        }
    }
}
