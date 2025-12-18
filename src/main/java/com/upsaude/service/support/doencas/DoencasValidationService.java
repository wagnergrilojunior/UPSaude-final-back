package com.upsaude.service.support.doencas;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.upsaude.api.request.clinica.doencas.DoencasRequest;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.clinica.doencas.DoencasRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoencasValidationService {

    private final DoencasRepository repository;

    public void validarObrigatorios(DoencasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da doença são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome da doença é obrigatório");
        }
    }

    public void validarDuplicidade(UUID id, DoencasRequest request) {
        if (request == null) {
            return;
        }

        if (StringUtils.hasText(request.getNome())) {
            String nome = request.getNome().trim();
            boolean duplicado = (id == null)
                ? repository.existsByNome(nome)
                : repository.existsByNomeAndIdNot(nome, id);
            if (duplicado) {
                throw new BadRequestException(String.format(
                    "Já existe uma doença cadastrada com o nome '%s' no banco de dados", request.getNome()));
            }
        }

        if (StringUtils.hasText(request.getCodigoInterno())) {
            String codigo = request.getCodigoInterno().trim();
            boolean duplicado = (id == null)
                ? repository.existsByCodigoInterno(codigo)
                : repository.existsByCodigoInternoAndIdNot(codigo, id);
            if (duplicado) {
                throw new BadRequestException(String.format(
                    "Já existe uma doença cadastrada com o código interno '%s' no banco de dados", request.getCodigoInterno()));
            }
        }
    }
}

