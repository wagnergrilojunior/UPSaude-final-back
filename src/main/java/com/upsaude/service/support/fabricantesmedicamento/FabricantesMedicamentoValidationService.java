package com.upsaude.service.support.fabricantesmedicamento;

import com.upsaude.api.request.FabricantesMedicamentoRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.FabricantesMedicamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FabricantesMedicamentoValidationService {

    private final FabricantesMedicamentoRepository repository;

    public void validarObrigatorios(FabricantesMedicamentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do fabricante de medicamento são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do fabricante de medicamento é obrigatório");
        }
    }

    public void validarUnicidadeParaCriacao(FabricantesMedicamentoRequest request) {
        validarUnicidade(null, request);
    }

    public void validarUnicidadeParaAtualizacao(UUID id, FabricantesMedicamentoRequest request) {
        validarUnicidade(id, request);
    }

    private void validarUnicidade(UUID id, FabricantesMedicamentoRequest request) {
        if (request == null) return;

        if (StringUtils.hasText(request.getNome())) {
            String nome = request.getNome().trim();
            boolean duplicado = (id == null) ? repository.existsByNome(nome) : repository.existsByNomeAndIdNot(nome, id);
            if (duplicado) {
                log.warn("Tentativa de cadastrar/atualizar fabricante de medicamento com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(String.format(
                    "Já existe um fabricante de medicamento cadastrado com o nome '%s' no banco de dados", request.getNome()
                ));
            }
        }
    }
}

