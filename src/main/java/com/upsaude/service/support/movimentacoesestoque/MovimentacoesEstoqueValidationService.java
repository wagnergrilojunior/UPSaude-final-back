package com.upsaude.service.support.movimentacoesestoque;

import com.upsaude.api.request.estoque.MovimentacoesEstoqueRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MovimentacoesEstoqueValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID da movimentação de estoque é obrigatório");
        }
    }

    public void validarObrigatorios(MovimentacoesEstoqueRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados da movimentação de estoque são obrigatórios");
        }
        if (request.getEstoqueVacina() == null) {
            throw new BadRequestException("Estoque de vacina é obrigatório");
        }
        if (request.getTipoMovimento() == null || request.getTipoMovimento().isBlank()) {
            throw new BadRequestException("Tipo de movimento é obrigatório");
        }
        if (request.getQuantidade() == null) {
            throw new BadRequestException("Quantidade é obrigatória");
        }
        if (request.getResponsavel() == null) {
            throw new BadRequestException("Responsável é obrigatório");
        }
    }
}
